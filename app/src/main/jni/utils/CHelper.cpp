#include "CHelper.h"

#include <vector>
#include <fstream>
#include <pthread.h>
#include <android/asset_manager.h>

#include <android/log.h>

#define  LOGI(...) __android_log_print(ANDROID_LOG_INFO, "APP", __VA_ARGS__)
#define  LOGE(...) __android_log_print(ANDROID_LOG_ERROR, "APP", __VA_ARGS__)

bool CHelper::ReadFile(AAssetManager *aAssetManager, const char *fileName,
                       std::vector<uint8_t> *buffer_ref) {
    std::ifstream f(fileName, std::ios::binary);
    if (f) {
        LOGI("reading:%s", fileName);
        f.seekg(0, std::ifstream::end);
        std::fpos<mbstate_t> fileSize = f.tellg();
        f.seekg(0, std::ifstream::beg);
        buffer_ref->reserve(fileSize);
        buffer_ref->assign(std::istreambuf_iterator<char>(f), std::istreambuf_iterator<char>());
        f.close();
        return true;
    } else {
        if (NULL == aAssetManager) {
            LOGE("AssetManager is null!");
            return false;
        }
        //Fallback to assetManager
        AAsset *assetFile = AAssetManager_open(aAssetManager, fileName, AASSET_MODE_BUFFER);
        if (!assetFile) {
            LOGE("assetFile is null: %s", fileName);
            return false;
        }
        uint8_t *data = (uint8_t *) AAsset_getBuffer(assetFile);
        int32_t size = AAsset_getLength(assetFile);
        if (data == NULL) {
            AAsset_close(assetFile);
            LOGE("Failed to load:%s", fileName);
            return false;
        }
        buffer_ref->reserve(size);
        buffer_ref->assign(data, data + size);
        AAsset_close(assetFile);
        return true;
    }
}


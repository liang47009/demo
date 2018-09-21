#ifndef _VULKAN_RENDERER_H_
#define _VULKAN_RENDERER_H_

#include <android/asset_manager.h>
#include "Singleton.h"
#include "IRenderer.h"

// Function pointer for getting physical device fetures to be enabled
typedef VkPhysicalDeviceFeatures (*PFN_GetEnabledFeatures)();

class VulkanRenderer : public Singleton<VulkanRenderer>, IRenderer {
public:
    VulkanRenderer();

    ~VulkanRenderer();

    bool init(AAssetManager *pManager);

    bool onChanged(int width, int height);

    void update();

    void onDrawFrame();

private:
    AAssetManager *m_AAssetManager;
    // Device features enabled by the example
    // If not set, no additional features are enabled (may result in validation layer errors)
    VkPhysicalDeviceFeatures enabledFeatures = {};

};

#endif//_VULKAN_RENDERER_H_
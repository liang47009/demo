#include <GLES2/gl2.h>
#include <assert.h>
#include <VulkanAndroid.h>
#include "freeimage.h"
#include "VulkanRenderer.h"

#ifndef ENABLEDFEATURESFN
#define ENABLEDFEATURESFN true
#endif

VulkanRenderer::VulkanRenderer() {
    bool libLoaded = loadVulkanLibrary();
    assert(libLoaded);
    PFN_GetEnabledFeatures enabledFeaturesFn;
    if (ENABLEDFEATURESFN) {
        this->enabledFeatures = enabledFeaturesFn();
    }

}

VulkanRenderer::~VulkanRenderer() {
}

bool VulkanRenderer::init(AAssetManager *pManager) {
    FreeImage_Initialise(true);
    return true;
}

bool VulkanRenderer::onChanged(int width, int height) {
    return true;
}

void VulkanRenderer::onDrawFrame() {
}

void VulkanRenderer::update() {

}

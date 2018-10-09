#include <GLES2/gl2.h>
#include <assert.h>
#include "VulkanAndroid.h"

#ifdef USE_FREEIMAGE
#include <freeimage/Source/FreeImage.h>
#endif

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
#ifdef USE_FREEIMAGE
    FreeImage_Initialise(true);
#endif
    return true;
}

bool VulkanRenderer::onChanged(int width, int height) {
    return true;
}

void VulkanRenderer::onDrawFrame() {
}

void VulkanRenderer::update() {

}

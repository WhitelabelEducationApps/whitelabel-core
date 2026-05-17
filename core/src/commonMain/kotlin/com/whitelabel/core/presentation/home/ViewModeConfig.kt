package com.whitelabel.core.presentation.home

import com.whitelabel.core.AppConfig

/**
 * Provides available view modes based on app configuration.
 * Map view is only available if enabled in AppConfig.
 */
fun AppConfig.getAvailableViewModes(): List<ViewMode> {
    return listOfNotNull(
        ViewMode.Grid,
        if (enableMap) ViewMode.Map else null
    )
}


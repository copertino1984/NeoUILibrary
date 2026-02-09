package com.cgsoftware.neoui.ui.components.common

enum class UiState {
    DISABLED,
    IDLE,        // stop/ready
    ACTIVE,      // play/running
    CONNECTING,  // sync/connecting
    ALERT        // attention/error/peak
}

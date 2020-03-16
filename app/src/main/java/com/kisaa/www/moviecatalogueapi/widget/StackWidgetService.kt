package com.kisaa.www.moviecatalogueapi.widget

import android.content.Intent
import android.widget.RemoteViewsService

class StackWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory =
        StackRemoteViewFactory(
            application,
            this.applicationContext
        )
}
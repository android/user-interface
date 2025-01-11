// Copyright 2022 Google Inc.
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// 
//      http://www.apache.org/licenses/LICENSE-2.0
// 
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.android_quick_settings;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;

import androidx.core.os.BuildCompat;

@SuppressLint("Override")
public class QSIntentService extends TileService {

    @Override
    public void onClick() {

        // Check to see if the device is currently locked.
        boolean isCurrentlyLocked = this.isLocked();

        if (!isCurrentlyLocked) {

            Resources resources = getApplication().getResources();

            Tile tile = getQsTile();
            String tileLabel = tile.getLabel().toString();
            String tileState = (tile.getState() == Tile.STATE_ACTIVE) ?
                    resources.getString(R.string.service_active) :
                    resources.getString(R.string.service_inactive);

            Intent intent = new Intent(getApplicationContext(),
                    ResultActivity.class);

            intent.putExtra(ResultActivity.RESULT_ACTIVITY_NAME_KEY,
                    tileLabel);
            intent.putExtra(ResultActivity.RESULT_ACTIVITY_INFO_KEY,
                    tileState);
            if (BuildCompat.isAtLeastP()) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }

            startActivityAndCollapse(intent);
        }
    }

}

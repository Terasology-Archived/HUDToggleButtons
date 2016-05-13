/*
 * Copyright 2014 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.HUDToggleButtons.ui;

import com.google.common.collect.Sets;
import org.terasology.HUDToggleButtons.systems.HUDToggleButtonsClientSystem;
import org.terasology.registry.In;
import org.terasology.rendering.nui.Canvas;
import org.terasology.rendering.nui.ControlWidget;
import org.terasology.rendering.nui.UIWidget;
import org.terasology.rendering.nui.databinding.ReadOnlyBinding;
import org.terasology.rendering.nui.layers.hud.CoreHudWidget;
import org.terasology.rendering.nui.layouts.ColumnLayout;
import org.terasology.rendering.nui.widgets.ActivateEventListener;
import org.terasology.rendering.nui.widgets.UIButton;

import java.util.Set;

public class HUDToggleButtons extends CoreHudWidget implements ControlWidget {
    @In
    private HUDToggleButtonsClientSystem toggleButtonsClientSystem;

    private ColumnLayout container;
    private Set<HUDToggleButtonsClientSystem.HUDToggleButtonState> addedStates = Sets.newHashSet();

    public void initialise() {
        container = find("container", ColumnLayout.class);
    }

    @Override
    public void onDraw(Canvas canvas) {
        for (HUDToggleButtonsClientSystem.HUDToggleButtonState state : toggleButtonsClientSystem.getToggleButtonStates()) {
            if (!addedStates.contains(state)) {
                container.addWidget(createWidget(state));
                addedStates.add(state);
            }
        }
        super.onDraw(canvas);
    }

    private UIWidget createWidget(final HUDToggleButtonsClientSystem.HUDToggleButtonState state) {
        UIButton button = new UIButton();
        button.bindText(new ReadOnlyBinding<String>() {
            @Override
            public String get() {
                return state.getText();
            }
        });
        button.subscribe(new ActivateEventListener() {
            @Override
            public void onActivated(UIWidget widget) {
                state.toggle();
            }
        });
        button.bindVisible(new ReadOnlyBinding<Boolean>() {
            @Override
            public Boolean get() {
                return state.isValid();
            }
        });
        return button;
    }
}

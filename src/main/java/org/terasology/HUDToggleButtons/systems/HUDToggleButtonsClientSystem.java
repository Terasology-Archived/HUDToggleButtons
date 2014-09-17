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
package org.terasology.HUDToggleButtons.systems;

import com.google.common.collect.Sets;
import org.terasology.entitySystem.systems.BaseComponentSystem;
import org.terasology.entitySystem.systems.RegisterMode;
import org.terasology.entitySystem.systems.RegisterSystem;
import org.terasology.registry.In;
import org.terasology.registry.Share;
import org.terasology.rendering.nui.NUIManager;

import java.util.Set;

@RegisterSystem(RegisterMode.CLIENT)
@Share(HUDToggleButtonsClientSystem.class)
public class HUDToggleButtonsClientSystem extends BaseComponentSystem {
    @In
    private NUIManager nuiManager;

    private Set<HUDToggleButtonState> toggleButtonStates = Sets.newHashSet();

    @Override
    public void preBegin() {
        nuiManager.getHUD().addHUDElement("HUDToggleButtons:HUDToggleButtons");
    }

    public void registerToggleButton(HUDToggleButtonState state) {
        toggleButtonStates.add(state);
    }

    public Iterable<HUDToggleButtonState> getToggleButtonStates() {
        return toggleButtonStates;
    }

    public interface HUDToggleButtonState {
        void toggle();

        boolean isValid();

        String getText();
    }
}

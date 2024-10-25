package com.starfish_studios.another_furniture.integration.neoforge;

import com.starfish_studios.another_furniture.integration.neoforge.create.CreateCompat;
import com.starfish_studios.another_furniture.registry.AFRegistry;

public class IntegrationHandlerImpl {

    public static void init() {
       if (AFRegistry.isModLoaded("create")) CreateCompat.setup();
    }
}

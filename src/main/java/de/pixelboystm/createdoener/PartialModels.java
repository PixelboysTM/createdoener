package de.pixelboystm.createdoener;

import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.Create;

public class PartialModels {
    public static final PartialModel DOENER_SPEAR_SHAFT = block("doener_spear_shaft");

    private static PartialModel block(String path) {
        return new PartialModel(CreateDoener.asResource("block/" + path));
    }

    public static void init() {}
}

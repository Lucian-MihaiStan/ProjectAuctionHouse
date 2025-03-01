package commander.addproduct;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import products.painting.Painting;

import java.util.List;

public class HelperAP {
    private HelperAP() {}

    public static Pair<String, String> setParamFurniture(List<String> restParameters) {
        return new ImmutablePair<>(restParameters.get(0), restParameters.get(1));
    }

    public static Pair<String, Painting.Colors> setParamPainting(List<String> restParameters) {
        return new ImmutablePair<>(restParameters.get(0), Painting.Colors.valueOf(restParameters.get(1)));
    }

    public static Pair<String, Boolean> setParamJewellery(List<String> restParameters) {
        return new ImmutablePair<>(restParameters.get(0), Boolean.valueOf(restParameters.get(1)));
    }
}

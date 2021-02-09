package commander.addproduct;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import products.painting.Painting;

import java.util.List;

/**
 * parse the rest of parameters depending on product type
 */
public class HelperAP {
    private HelperAP() {}

    /**
     * parse the rest of parameters of furniture product type
     * @param restParameters rest of parameters
     * @return a list of string, string (type, material)
     */
    public static Pair<String, String> setParamFurniture(List<String> restParameters) {
        return new ImmutablePair<>(restParameters.get(0), restParameters.get(1));
    }

    /**
     * parse the rest of parameters of painting product type
     * @param restParameters rest of parameters
     * @return a list of string, colors (artist name, colors)
     */
    public static Pair<String, Painting.Colors> setParamPainting(List<String> restParameters) {
        return new ImmutablePair<>(restParameters.get(0), Painting.Colors.valueOf(restParameters.get(1)));
    }

    /**
     * parse the rest of parameters of painting product type
     * @param restParameters rest of parameters
     * @return a list of string, boolean (material, gemstone)
     */
    public static Pair<String, Boolean> setParamJewellery(List<String> restParameters) {
        return new ImmutablePair<>(restParameters.get(0), Boolean.valueOf(restParameters.get(1)));
    }
}

package commander.createuser;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * parser for the parameters of create user
 */
public class HelperCU {
    private HelperCU() {}

    /**
     * parse the parameters of create users
     * @param param parameters of individual person
     * @return birth date
     */
    public static Date convertParamIP(List<String> param) {
        Date result = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            result = dateFormat.parse(param.get(0));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * parse the parameters of create users
     * @param restParameters parameters of legal person
     * @return pair double, string (social capital, company type)
     */
    public static Pair<Double, String> convertParamLP(List<String> restParameters) {
        return new ImmutablePair<>(Double.parseDouble(restParameters.get(0)), restParameters.get(1));
    }
}

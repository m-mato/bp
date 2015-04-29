package application;

/**
 * Class AccCreator. Creates get and set methods - String templates.
 *
 * Should note be used by any class outside this package.
 *
 * @author Matej Majdis
 */
public class AccCreator {

    public static String makeGetTemplate(String fieldName, String fieldType, String returnType) {
        final String upName = fieldName.toUpperCase();
        final String header = "public static " + fieldType + " get" + upName + "StatGenerated(Object obj)";
        final String bodyRetype = returnType + " ret = (" + returnType + ") obj;";
        final String bodyReturn = "return ret." + fieldName + ";";
        final String body = bodyRetype + "\n" + "\n" + bodyReturn;

        final String getTemplate = header + " {\n" + body + "\n}";

        return getTemplate;
    }

    public static String makeSetTemplate(String fieldName, String fieldType, String parameterType) {
        final String upName = fieldName.toUpperCase();
        final String header = "public static void set" + upName + "StatGenerated(Object obj, " + fieldType + " " + fieldName + ")";
        final String bodyRetype = parameterType + " t = (" + parameterType + ") obj;";
        final String bodySet = "t." + fieldName + " = " + fieldName + ";";
        final String body = bodyRetype + bodySet;

        final String setTemplate = header + " {\n" + body + "\n}";

        return setTemplate;

    }

}

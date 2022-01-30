import java.lang.reflect.Field;

interface CreateObjectsInterface {
    default Pen createObjectUsingOneArgumentConstructor(Integer ink) {
        return new Pen(ink);
    }

    default Pen createObjectUsingTwoArgumentConstructor(Integer ink, Double size) {
        return new Pen(ink, size);
    }

    default Pen createObjectUsingThreeArgumentConstructor(Integer ink, Double size, String color) {
        return new Pen(ink, size, color);
    }
}

interface GetFieldsValuesInterface {
    default String getFieldValue(Pen obj, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(obj).toString();
    }

    default String[] getAllFieldsValues(Pen obj) throws IllegalAccessException {
        Field[] allFields = obj.getClass().getDeclaredFields();
        String[] ArrayWithFieldsValues = new String[allFields.length];

        for (int i = 0; i < allFields.length; i++) {
            allFields[i].setAccessible(true);
            ArrayWithFieldsValues[i] = allFields[i].get(obj).toString();
        }
        return ArrayWithFieldsValues;
    }
}


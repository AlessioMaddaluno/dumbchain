package dumbchain;

/**
 * Define a serializable object interface.
 */
public interface Serialized {
    /**
     * A serializble object returns a String rappresentation of the object itself.
     * Unlike the Object.ToString() override, it's recommended a JSON serialize.
     *
     * @return a JSON serialized
     */
    String serialize();
}

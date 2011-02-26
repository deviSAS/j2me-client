package com.devi.os.json;

public class JSONStringer extends JSONWriter {
    /**
     * Make a fresh JSONStringer. It can be used to build one JSON text.
     */
    public JSONStringer() {
        super(new StringWriter());
    }

    /**
     * Return the JSON text. This method is used to obtain the product of the
     * JSONStringer instance. It will return <code>null</code> if there was a
     * problem in the construction of the JSON text (such as the calls to
     * <code>array</code> were not properly balanced with calls to
     * <code>endArray</code>).
     * @return The JSON text.
     */
    public String toString() {
        return this.mode == 'd' ? this.writer.toString() : null;
    }
}

package com.nasumilu.calculator.data;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public interface NodeBuilder {

    /**
     * Builds an Element with argument Document
     *
     * @param document
     * @return Element
     */
    Element build(Document document);

}

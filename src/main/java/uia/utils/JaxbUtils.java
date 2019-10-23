/*******************************************************************************
 * Copyright 2017 UIA
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package uia.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLFilterImpl;

public class JaxbUtils<T> {
	
    private Unmarshaller unmarshaller;

    private Marshaller marshaller;

    private String namespace;

    private String rootElement;

    private Class<T> cls;

    public JaxbUtils(Class<T> cls, String rootElement, String namespace, String packageName) throws JAXBException {
        this(cls, rootElement, namespace, packageName, null);
    }

    public JaxbUtils(Class<T> cls, String rootElement, String namespace, String packageName, Schema schema) throws JAXBException {
        this.cls = cls;
        this.rootElement = rootElement;
        this.namespace = namespace;

        JAXBContext jc = JAXBContext.newInstance(packageName);
        this.unmarshaller = jc.createUnmarshaller();
        if (schema != null) {
            this.unmarshaller.setSchema(schema);
        }

        this.marshaller = jc.createMarshaller();
        this.marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8");
        this.marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        if (schema != null) {
            this.marshaller.setSchema(schema);
        }
    }

    public String toXml(T value) throws Exception {
        ByteArrayOutputStream o = new ByteArrayOutputStream();
        this.marshaller.marshal(new JAXBElement<T>(new QName(this.namespace, this.rootElement), this.cls, value), o);
        String xml = new String(o.toByteArray(), "utf-8");
        o.close();
        return xml;
    }

    public void toXml(T value, String path) throws Exception {
        this.marshaller.marshal(new JAXBElement<T>(new QName(this.namespace, this.rootElement), this.cls, value), new File(path));
    }

    public T fromXml(File file) throws Exception {
        return fromXml(new FileInputStream(file));
    }

    public T fromXml(InputStream stream) throws Exception {
        // Create the XMLReader
        SAXParserFactory factory = SAXParserFactory.newInstance();
        XMLReader reader = factory.newSAXParser().getXMLReader();

        // The filter class to set the correct namespace
        XMLFilterImpl xmlFilter = new XMLNamespaceFilter(reader);
        reader.setContentHandler(this.unmarshaller.getUnmarshallerHandler());

        SAXSource source = new SAXSource(xmlFilter, new InputSource(stream));

        @SuppressWarnings("unchecked")
        JAXBElement<T> elem = (JAXBElement<T>) this.unmarshaller.unmarshal(source);
        return elem.getValue();
    }

    public T fromXml(String content) throws Exception {

        // Create the XMLReader
        SAXParserFactory factory = SAXParserFactory.newInstance();
        XMLReader reader = factory.newSAXParser().getXMLReader();

        // The filter class to set the correct namespace
        XMLFilterImpl xmlFilter = new XMLNamespaceFilter(reader);
        reader.setContentHandler(this.unmarshaller.getUnmarshallerHandler());

        InputStream inStream = new ByteArrayInputStream(content.getBytes("UTF-8"));
        SAXSource source = new SAXSource(xmlFilter, new InputSource(inStream));

        @SuppressWarnings("unchecked")
        JAXBElement<T> elem = (JAXBElement<T>) this.unmarshaller.unmarshal(source);
        return elem.getValue();
    }

    /**
     *
     * @author Kyle K. Lin
     *
     */
    public class XMLNamespaceFilter extends XMLFilterImpl {

        public XMLNamespaceFilter(XMLReader reader) {
            super(reader);
        }

        @Override
        public void startElement(String uri, String localName, String qName, org.xml.sax.Attributes attributes) throws SAXException {
            super.startElement(JaxbUtils.this.namespace, localName, qName, attributes);
        }
    }
}

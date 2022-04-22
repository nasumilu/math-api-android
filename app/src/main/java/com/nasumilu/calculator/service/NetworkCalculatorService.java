package com.nasumilu.calculator.service;

import com.nasumilu.calculator.data.model.Equation;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class NetworkCalculatorService {

    private final CalculatorService SERVICE;

    public NetworkCalculatorService(final String token) {
        var client = new OkHttpClient.Builder()
                .addInterceptor((chain) -> {
                    var r = chain.request().newBuilder()
                            .header("Authorization", "Bearer " + token)
                            .build();

                    return chain.proceed(r);
                }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("https://nasumilu.io/")
                .addConverterFactory(ScalarsConverterFactory.create())
                //.addConverterFactory(JaxbConverterFactory.create())
                .build();

        SERVICE = retrofit.create(CalculatorService.class);

    }


    public void calculate(Equation equation) throws IOException {
        var xml = this.xml(equation);
        var call = SERVICE.calculate(xml);
        var response = call.execute();
        if (response.isSuccessful()) {
            equation.clear();
            try {
                equation.append(Double.parseDouble(this.result(response.body())));
            } catch(NumberFormatException ex) { }
        } else {
            equation.append("Err");
        }
    }

    private String result(String response) {
        try {
            var document = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(new InputSource(new StringReader(response)));
            var nodes = document.getElementsByTagName("mn");
            if(nodes.getLength() == 1) {
                return nodes.item(0).getTextContent();
            }
        } catch (ParserConfigurationException |  IOException | SAXException e) {
            e.printStackTrace();
        }
        return "Err";
    }

    public String xml(Equation equation) {
        try {
            Document document = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .newDocument();
            var root = document.createElement("math");
            document.appendChild(root);
            root.appendChild(equation.build(document));

            DOMSource source = new DOMSource(document);
            StringWriter out = new StringWriter();
            StreamResult stream = new StreamResult(out);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "no");
            transformer.transform(source, stream);
            return out.getBuffer().toString();
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
        return "";
    }
}

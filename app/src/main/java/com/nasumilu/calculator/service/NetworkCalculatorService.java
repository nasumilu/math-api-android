package com.nasumilu.calculator.service;

import android.util.Log;

import com.nasumilu.calculator.data.model.Equation;

import org.w3c.dom.Document;

import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class NetworkCalculatorService {

    private final CalculatorService SERVICE;

    public NetworkCalculatorService() {
        Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://nasumilu.io/")
                .addConverterFactory(ScalarsConverterFactory.create())
                //.addConverterFactory(JaxbConverterFactory.create())
                .build();

        SERVICE = retrofit.create(CalculatorService.class);

    }


    public String calculate(Equation equation) throws IOException {
        var xml = this.xml(equation);
        var call = SERVICE.calculate(xml);
        Log.i("Request Body:", xml);
        Log.println(Log.ASSERT, "CustomTag", "LogMessage");
        var response = call.execute().body();
        Log.i("Math-API/Response", response);
        return "value";
    }

    public String xml(Equation equation) {
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
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

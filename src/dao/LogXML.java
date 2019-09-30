/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import model.Log;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author marcos
 */
public class LogXML extends LogDAO {

    private File arquivoXml;
    private String path;
    private DocumentBuilderFactory dbf;
    private DocumentBuilder db;
    private Document doc;

    public LogXML() throws ParserConfigurationException, SAXException, IOException {
        super();
        path = "src/data/logs.xml";
        arquivoXml = new File(path);
        dbf = DocumentBuilderFactory.newInstance();
        db = dbf.newDocumentBuilder();
//        doc = db.parse(arquivoXml);
    }

    @Override
    public void ler() {
    }

    @Override
    public void escrever() {
        Element raiz = null;
        try {
            doc = db.parse(arquivoXml);
            raiz = doc.getDocumentElement();
        } catch (SAXException ex) {
            doc = db.newDocument();
            raiz = doc.createElement("logs");
            doc.appendChild(raiz);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        //para voltar é só apagar essa linha de cima e descomentar no construtor
        
        try {
            //obtendo o elemento raiz do arquivo.xml
//            Element raiz = doc.getDocumentElement();

            //construindo a estrutura do documento xml
            for (Log l : collection.getLogs()) {
                Element elementoLog = doc.createElement("log");
                
                //elementos que compoem um log
                Element usuario = doc.createElement("usuario");
                usuario.appendChild(doc.createTextNode(l.getUsuario()));
                elementoLog.appendChild(usuario);

                Element data = doc.createElement("data");
                data.appendChild(doc.createTextNode(l.getData().format(DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm:ss"))));
                elementoLog.appendChild(data);

                Element funcionario = doc.createElement("funcionario");
                funcionario.appendChild(doc.createTextNode(l.getNomeDoFuncionario()));
                elementoLog.appendChild(funcionario);

                Element operacao = doc.createElement("operacao");
                operacao.appendChild(doc.createTextNode(l.getOperacao()));
                elementoLog.appendChild(operacao);

                raiz.appendChild(elementoLog);
            }

            //preparando para escrever no arquivo
            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformer = transFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(arquivoXml);

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(source, result);

        } catch (TransformerConfigurationException ex) {
            ex.printStackTrace();
        } catch (TransformerException ex) {
            ex.printStackTrace();
        }
    }
}

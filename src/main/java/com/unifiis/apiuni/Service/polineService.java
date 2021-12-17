package com.unifiis.apiuni.Service;

import com.unifiis.apiuni.Beans.Iris;
import com.unifiis.apiuni.Beans.Polineuropatia;
import org.springframework.stereotype.Service;
import weka.classifiers.Classifier;
import weka.classifiers.functions.LibSVM;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.List;

@Service
public class polineService {

    public String consultar(Polineuropatia param) {
        String rpta = "";
        // we need those for creating new instances later
        final Attribute attribute_Edad = new Attribute("Edad");
        final Attribute attribute_Genero = new Attribute("Genero");
        final Attribute attribute_flgCovid = new Attribute("flgCovid");
        final Attribute attribute_UCI = new Attribute("UCI");
        final Attribute attribute_flgDiabetes = new Attribute("flgDiabetes");
        final Attribute attribute_TipoExtremidad = new Attribute("TipoExtremidad");
        final Attribute attribute_Latencia_1_der = new Attribute("Latencia_1_der");
        final Attribute attribute_Amplitud_1_der = new Attribute("Amplitud_1_der");
        final Attribute attribute_Latencia_2_der = new Attribute("Latencia_2_der");
        final Attribute attribute_Amplitud_2_der = new Attribute("Amplitud_2_der");
        final Attribute attribute_Latencia_1_izq = new Attribute("Latencia_1_izq");
        final Attribute attribute_Amplitud_1_izq = new Attribute("Amplitud_1_izq");
        final Attribute attribute_Latencia_2_izq = new Attribute("Latencia_2_izq");
        final Attribute attribute_Amplitud_2_izq = new Attribute("Amplitud_2_izq");
        final Attribute attribute_Amp_Needle_1_der = new Attribute("Amp_Needle_1_der");
        final Attribute attribute_Amp_Needle_2_der = new Attribute("Amp_Needle_2_der");
        final Attribute attribute_Amp_Needle_3_der = new Attribute("Amp_Needle_3_der");
        final Attribute attribute_Amp_Needle_1_izq = new Attribute("Amp_Needle_1_izq");
        final Attribute attribute_Amp_Needle_2_izq = new Attribute("Amp_Needle_2_izq");
        final Attribute attribute_Amp_Needle_3_izq = new Attribute("Amp_Needle_3_izq");

        final List<String> classes = new ArrayList<String>() {
            {
                add("SI");
                add("NO");
            }
        };
        // Instances(...) requires ArrayList<> instead of List<>...
        ArrayList<Attribute> attributeList = new ArrayList<Attribute>(2) {
            {
                add(attribute_Edad);
                add(attribute_Genero);
                add(attribute_flgCovid);
                add(attribute_UCI);
                add(attribute_flgDiabetes);
                add(attribute_TipoExtremidad);
                add(attribute_Latencia_1_der);
                add(attribute_Amplitud_1_der);
                add(attribute_Latencia_2_der);
                add(attribute_Amplitud_2_der);
                add(attribute_Latencia_1_izq);
                add(attribute_Amplitud_1_izq);
                add(attribute_Latencia_2_izq);
                add(attribute_Amplitud_2_izq);
                add(attribute_Amp_Needle_1_der);
                add(attribute_Amp_Needle_2_der);
                add(attribute_Amp_Needle_3_der);
                add(attribute_Amp_Needle_1_izq);
                add(attribute_Amp_Needle_2_izq);
                add(attribute_Amp_Needle_3_izq);
                Attribute attributeClass = new Attribute("@@class@@", classes);
                add(attributeClass);
            }
        };
        // unpredicted data sets (reference to sample structure for new instances)
        Instances dataUnpredicted = new Instances("TestInstances",
                attributeList, 1);
        // last feature is target variable
        dataUnpredicted.setClassIndex(dataUnpredicted.numAttributes() - 1);

        // create new instance: this one should fall into the setosa domain
        DenseInstance newInstance = new DenseInstance(dataUnpredicted.numAttributes()) {
            {
                setValue(attribute_Edad,param.getEdad());
                setValue(attribute_Genero,param.getGenero());
                setValue(attribute_flgCovid,param.getFlgCovid());
                setValue(attribute_UCI,param.getUCI());
                setValue(attribute_flgDiabetes,param.getFlgDiabetes());
                setValue(attribute_TipoExtremidad,param.getTipoExtremidad());
                setValue(attribute_Latencia_1_der,param.getLatencia_1_der());
                setValue(attribute_Amplitud_1_der,param.getAmplitud_1_der());
                setValue(attribute_Latencia_2_der,param.getLatencia_2_der());
                setValue(attribute_Amplitud_2_der,param.getAmplitud_2_der());
                setValue(attribute_Latencia_1_izq,param.getLatencia_1_izq());
                setValue(attribute_Amplitud_1_izq,param.getAmplitud_1_izq());
                setValue(attribute_Latencia_2_izq,param.getLatencia_2_izq());
                setValue(attribute_Amplitud_2_izq,param.getAmplitud_2_izq());
                setValue(attribute_Amp_Needle_1_der,param.getAmp_Needle_1_der());
                setValue(attribute_Amp_Needle_2_der,param.getAmp_Needle_2_der());
                setValue(attribute_Amp_Needle_3_der,param.getAmp_Needle_3_der());
                setValue(attribute_Amp_Needle_1_izq,param.getAmp_Needle_1_izq());
                setValue(attribute_Amp_Needle_2_izq,param.getAmp_Needle_2_izq());
                setValue(attribute_Amp_Needle_3_izq,param.getAmp_Needle_3_izq());
            }
        };
        // reference to dataset
        newInstance.setDataset(dataUnpredicted);
        // import ready trained model
        Classifier cls = null;
        LibSVM wekaClassifier = new LibSVM();
        try {
            cls = (Classifier) weka.core.SerializationHelper
                    //.read("E:/Polineuropatia-J48.model");
                    .read("E:/Polineuropatia-RandomForest.model");
                    //.read("E:/Polineuropatia-Bayes.model");
//                    .read("E:/Polineuropatia-SVM.model");

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (cls == null)
            return "null";

        // predict new sample
        try {
            double result = cls.classifyInstance(newInstance);
            rpta = classes.get(new Double(result).intValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rpta;
    }
}

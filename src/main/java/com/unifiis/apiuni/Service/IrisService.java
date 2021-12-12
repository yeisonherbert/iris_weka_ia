package com.unifiis.apiuni.Service;

import com.unifiis.apiuni.Beans.Iris;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.List;

@Service
public class IrisService {

    public String consultar(Iris iris) {
        String rpta = "";
        // we need those for creating new instances later
        final Attribute attributeSepalLength = new Attribute("sepallength");
        final Attribute attributeSepalWidth = new Attribute("sepalwidth");
        final Attribute attributePetalLength = new Attribute("petallength");
        final Attribute attributePetalWidth = new Attribute("petalwidth");

        final List<String> classes = new ArrayList<String>() {
            {
                add("Iris-setosa");
                add("Iris-versicolor");
                add("Iris-virginica");

            }
        };
        // Instances(...) requires ArrayList<> instead of List<>...
        ArrayList<Attribute> attributeList = new ArrayList<Attribute>(2) {
            {
                add(attributeSepalLength);
                add(attributeSepalWidth);
                add(attributePetalLength);
                add(attributePetalWidth);
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
                setValue(attributeSepalLength, iris.getSepallength());
                setValue(attributeSepalWidth, iris.getSepalwidth());
                setValue(attributePetalLength, iris.getPetallength());
                setValue(attributePetalWidth, iris.getPetalwidth());
            }
        };
        // reference to dataset
        newInstance.setDataset(dataUnpredicted);
        // import ready trained model
        Classifier cls = null;
        try {
            cls = (Classifier) weka.core.SerializationHelper
                    .read("E:/iris.model");
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

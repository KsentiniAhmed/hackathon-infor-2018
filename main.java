/*last submit*/
import java.io.*;
import java.util.*;

public class Main {
    //CSV file header
    private static final String FILE_HEADER = "Store_ID,Item_ID,Friday_End,Units,Sales";


    public static void main(String[] args) {

        Scanner keyboard = new Scanner(System.in);
        System.out.println("enter type of repartition : Item_ID or Category_ID or Department_ID");
        String methode = keyboard.next();
        //C:\Users\ahmed\Desktop\hack2018\src\sample_data\sales.csv
        System.out.println("enter the path of sales.csv ");
        String pathSales = keyboard.next();
        //C:\Users\ahmed\Desktop\hack2018\src\sample_data\product.csv
        System.out.println("enter the path of product.csv ");
        String pathProduct = keyboard.next();
        // C:\Users\ahmed\Desktop\hack2018\src

        System.out.println("enter the path to loadfile ");
        String pathload = keyboard.next();

        /*TODO: use a simple funtion that input is file name and the output is a map or list*/
        Map<String, ArrayList<String>> mapSales = new HashMap<String, ArrayList<String>>();

        Map<String, String> mapProduct = new HashMap<String, String>();
        Map<String, ArrayList<String>> mapProductCategorie = new HashMap<String, ArrayList<String>>();
        Map<String, ArrayList<String>> mapProductDepartement = new HashMap<String, ArrayList<String>>();

        ArrayList<String> listSales = new ArrayList<String>();
        ArrayList<String> listProduct = new ArrayList<String>();

        //String csvFileSales = "C:\\Users\\ahmed\\Desktop\\hack2018\\src\\sample_data\\sales.csv";
        String csvFileSales = pathSales;
        //String csvFileProduct = "C:\\Users\\ahmed\\Desktop\\hack2018\\src\\sample_data\\product.csv";
        String csvFileProduct = pathProduct;
        BufferedReader br = null;
        BufferedReader br1 = null;
        String line = "";
        String cvsSplitBy = ",";

        try {

            br = new BufferedReader(new FileReader(csvFileSales));
            br1 = new BufferedReader(new FileReader(csvFileProduct));

            while ((line = br1.readLine()) != null) {

                // use comma as separator
                String[] product = line.split(cvsSplitBy);
                String productLine = String.join(",", product);
                listProduct.add(productLine);
                System.out.println("produit [Item_ID= " + product[0] + " , Category_ID=" + product[1] + " , Department_ID=" + product[2] + "]");

                mapProduct.put(product[0], String.join(",", product));
                if (methode.equals("Category_ID")) {
                    ArrayList<String> l1 = new ArrayList<String>();
                    if (mapProductCategorie.get(product[1]) != null) {
                        l1 = mapProductCategorie.get(product[1]);

                    }

                    l1.add(productLine);
                    mapProductCategorie.put(product[1], l1);
                }
                if (methode.equals("Department_ID")) {
                    ArrayList<String> l1 = new ArrayList<String>();
                    if (mapProductDepartement.get(product[2]) != null) {
                        l1 = mapProductDepartement.get(product[2]);

                    }

                    l1.add(productLine);
                    mapProductDepartement.put(product[2], l1);
                }            }

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] sale = line.split(cvsSplitBy);
                String salesLine = String.join(",", sale);

                listSales.add(salesLine);

                System.out.println("sale [Store_ID= " + sale[0] + " , Item_ID=" + sale[1] + " , Sales=" + sale[4] + "]");
                ArrayList<String> l1 = new ArrayList<String>();
                if (mapSales.get(sale[1]) != null) {
                    l1 = mapSales.get(sale[1]);

                }
                l1.add(salesLine);
                mapSales.put(sale[1], l1);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(mapSales.get("1"));
        System.out.println(mapProduct.get("1"));
        System.out.println(mapProductCategorie.get("c01"));
        System.out.println(mapProductDepartement.get("dep01"));


        for (String keyProd : mapProduct.keySet()) {
            for (String keySale : mapSales.keySet()) {
                if (keyProd.equals(keySale)) {

                    System.out.println("Found the key in product : " + keyProd + " like the key in Sale : "+keySale+" result : " + mapSales.get(keySale));

                    if (methode.equals("Item_ID")) {

                            String fileName = pathload + "\\"+ keySale;
                        if(keySale.equals("Item_ID")) {

                            new File(fileName).mkdir();

                            System.out.println(mapSales.get(keySale).size());
                            createCsvFile(mapSales.get(keySale), fileName + "\\sales.csv");
                        }
                    }
                    else if (methode.equals("Category_ID")) {

                        String lineP =mapProduct.get(keySale);
                        String tmp[] = lineP.toString().split(",");
                        if(!tmp[1].equals("Category_ID")) {
                            String fileName = pathload + "\\" + tmp[1];
                            new File(fileName).mkdir();

                        //System.out.println(mapProductCategorie.get(keySale).size());
                        createCsvFile(mapSales.get(keySale), fileName + "\\sales.csv");
                        }
                    }
                    else if (methode.equals("Department_ID")) {

                        String lineP =mapProduct.get(keySale);
                        String tmp[] = lineP.toString().split(",");
                        if(!tmp[2].equals("Department_ID")) {
                            String fileName = pathload + "\\" + tmp[2];
                            new File(fileName).mkdir();
                            createCsvFile(mapSales.get(keySale), fileName + "\\sales.csv");
                        }
                    }
                }
            }
        }
    }

    public static void createCsvFile(ArrayList<String> l1, String fileName) {

        FileWriter fileWriter = null;
        Writer output = null;

        try {

            File file =new File(fileName);
            double bytes = file.length();

                //Write the CSV file header
            if (bytes== 0) {
                fileWriter = new FileWriter(fileName,true);

                fileWriter.append(FILE_HEADER.toString());

                //Add a new line separator after the header
                fileWriter.append("\n");
                for (String ligne : l1) {

                    fileWriter.append(ligne);
                    fileWriter.append("\n");

                }
            }else {
            fileWriter = new FileWriter(fileName,true);

            //Write a new student object list to the CSV file
                for (String ligne : l1) {

                    fileWriter.append(ligne);
                    fileWriter.append("\n");

                }
            }
          /*  }
            else {
                for (String ligne : l1) {

                    output.append(ligne);
                    output.append("\n");

                }
                }
                */


            System.out.println("CSV file was created successfully !!!");

        } catch (Exception e) {
            System.out.println("Error in CsvFileWriter !!!");
            e.printStackTrace();
        } finally {

            try {
                if(fileWriter !=null) {
                    fileWriter.flush();
                    fileWriter.close();
                    if(output!=null){
                        output.close();
                    }
                }
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
            }

        }

    }

}

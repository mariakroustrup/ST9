package Algoritme;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 *
 * @author Maria
 */
public class algoritme {

    private static int a; // Kolonne nummer for Navn Foer
    private static int b; // Kolonne nummer for Navn Nu
    private static int c; // Kolonne nummer for Dispform Foer
    private static int d; // Kolonne nummer for Dispform Nu
    private static int e; // Kolonne nummer for Styrke Foer
    private static int f; // Kolonne nummer for Styrke Nu
    private static int atc; // kolonne nummer for ATCkoder
  
    private static String Status; // Begrundelse for risikoscore
    private static String nyStatus; // Begrundelse som opdateres hver gang
    private static double score;   // Risikoscore
    private static double nyScore; // Risikoscore som opdateres hver gang
    private static Label label = null; // Label som udskrives output der indeholder risikoscore og begrundelse
    
    static String[][] ElementAMGROS18; // Array til elementer i Amgrosskift fra 2017 til 2018 (skift sker i 2018)
    static String[][] ElementAMGROS17; // Array til elementer i Amgrosskift fra 2016 til 2017 (skift sker i 2017)
    static String[][] ElementAMGROS16; // Array til elementer i Amgrosskift fra 2015 til 2016 (skift sker i 2016)
    static String[][] ElementAMGROS15; // Array til elementer i Amgrosskift fra 2014 til 2015 (skift sker i 2015)
    static String[][] ElementAMGROS14; // Array til elementer i Amgrosskift fra 2013 til 2014 (skift sker i 2014)
    static String[][] ElementAMGROS; // Array til elementer i Amgrosskift som skal risikovurderes 
    static String[][] ElementSRN; // // Array til elementer i udbudsmateriale
    static String[][] ElementRisiko; // Array til elementer i risikolaegemidler


    public static void main(final String[] args) throws BiffException, IOException, RowsExceededException, WriteException {
        // Indlaeser excel input
        FileInputStream AMGROS = new FileInputStream("/Users/Maria/Desktop/Amgrosskift.xls");
        Workbook wb = Workbook.getWorkbook(AMGROS);
        Sheet shAMGROS = wb.getSheet(0);
       
        // Indlaeser excel med data om skift fra 2017 til 2018
        Workbook wb18 = Workbook.getWorkbook(new FileInputStream("/Users/Maria/Desktop/Amgrosskift2018.xls"));
        Sheet shAMGROS18 = wb18.getSheet(0);
        
        // Indlaeser excel med data om skift fra 2016 til 2017
        Workbook wb17 = Workbook.getWorkbook(new FileInputStream("/Users/Maria/Desktop/Amgrosskift2017.xls"));
        Sheet shAMGROS17 = wb17.getSheet(0);

        // Indlaeser excel med data om skift fra 2015 til 2016
        Workbook wb16 = Workbook.getWorkbook(new FileInputStream("/Users/Maria/Desktop/Amgrosskift2016.xls"));
        Sheet shAMGROS16 = wb16.getSheet(0);

        // Indlaeser excel med data om skift fra 2014 til 2015
        Workbook wb15 = Workbook.getWorkbook(new FileInputStream("/Users/Maria/Desktop/Amgrosskift2015.xls"));
        Sheet shAMGROS15 = wb15.getSheet(0);

        // Indlaeser excel med data om skift fra 2013 til 2014
        Workbook wb14 = Workbook.getWorkbook(new FileInputStream("/Users/Maria/Desktop/Amgrosskift2014.xls"));
        Sheet shAMGROS14 = wb14.getSheet(0);

        // Udbudsmateriale fra SRN
        Workbook wbSRN = Workbook.getWorkbook(new FileInputStream("/Users/Maria/Desktop/SRN.xls"));
        Sheet shSRN = wbSRN.getSheet(0);

        // Risikolaegemidler fra Amgros
        Workbook wbRisiko = Workbook.getWorkbook(new FileInputStream("/Users/Maria/Desktop/Risiko.xls"));
        Sheet shRisiko = wbRisiko.getSheet(0);

        // Opretter excelark til output ud fra det input excel der indlaeses
			WritableWorkbook ww = Workbook.createWorkbook(new File("/Users/Maria/Desktop/Amgrosskiftendelig.xls"),
                wb);
        WritableSheet ws = ww.getSheet(0);
    
        ElementAMGROS = new String[shAMGROS.getColumns()][shAMGROS.getRows()];
        ElementAMGROS18 = new String[shAMGROS18.getColumns()][shAMGROS18.getRows()]; // Elementer for Amgrosskift i 2018
        ElementAMGROS17 = new String[shAMGROS17.getColumns()][shAMGROS17.getRows()]; // Elementer for Amgrosskift i 2017
        ElementAMGROS16 = new String[shAMGROS16.getColumns()][shAMGROS16.getRows()]; // Elementer for Amgrosskift i 2016
        ElementAMGROS15 = new String[shAMGROS15.getColumns()][shAMGROS15.getRows()]; // Elementer for Amgrosskift i 2015
        ElementAMGROS14 = new String[shAMGROS14.getColumns()][shAMGROS14.getRows()]; // Elementer for Amgrosskift i 2014
        ElementSRN      = new String[shSRN.getColumns()][shSRN.getRows()]; // Elementer for udbudsmateriale
        ElementRisiko   = new String[shRisiko.getColumns()][shRisiko.getRows()]; // Elementer for risikolaegemidler

        ArrayList<String> NavnList = new ArrayList<>(); // ArrayList til look-a-like
        ArrayList<String> RisikoList = new ArrayList<>(); // ArrayList til risikolaegemidler 
        ArrayList<String> VarenavnList = new ArrayList<>(); // ArrayList til navne fra udbudsmateriale
        ArrayList<String> MEDList = new ArrayList<>(); // ArrayList til laegemidler ens med input
        ArrayList<String> MRList = new ArrayList<>();  // ArrayList til laegemidler som indgaar i Medicinraadet
        ArrayList<String> ATCkoderList = new ArrayList<>(); // ArrayList til ATC-koder
        ATCkoderList.add("A01");
        ATCkoderList.add("B05"); 
        ATCkoderList.add("J01");
        ATCkoderList.add("J06"); 
        ATCkoderList.add("L01"); 
        ATCkoderList.add("N01"); 
       
			// RISIKOLAEGEMIDLER
        int totalNoOfRowsRisiko = shRisiko.getRows();
        int totalNoOfColsRisiko = shRisiko.getColumns();
        for (int rowRisiko = 0; rowRisiko < totalNoOfRowsRisiko; rowRisiko++) {
            for (int colRisiko = 0; colRisiko < totalNoOfColsRisiko; colRisiko++) {
                if (shRisiko.getCell(colRisiko, rowRisiko).getContents().startsWith("ATC")) {
                    int cRisiko = colRisiko;
                    for (int y = 1; y < shRisiko.getRows(); y++) {
                        Cell cellRisiko = shRisiko.getCell(cRisiko, y);
                        ElementRisiko[cRisiko][y] = cellRisiko.getContents();
                        String elemRisiko = cellRisiko.getContents();
                        RisikoList.add(elemRisiko);
                    }
                }
            }
        }
        
			// VARENAVNE I UDBUDSMATERIALE
        int totalNoOfRowsSRN = shSRN.getRows();
        int totalNoOfColsSRN = shSRN.getColumns();
        for (int rowSRN = 0; rowSRN < totalNoOfRowsSRN; rowSRN++) {
            for (int colSRN = 0; colSRN < totalNoOfColsSRN; colSRN++) {
                if (shSRN.getCell(colSRN, rowSRN).getContents().startsWith("Varenavn")) {
                    int cSRN = colSRN;
                    for (int y = 1; y < shSRN.getRows(); y++) {
                        Cell cellSRN = shSRN.getCell(cSRN, y);
                        ElementSRN[cSRN][y] = cellSRN.getContents();
                        String elemSRN = cellSRN.getContents();
                        elemSRN = elemSRN.substring(0).toLowerCase();
                        elemSRN = elemSRN.replace(",", " ");
                        elemSRN = elemSRN.replace("-", "");
                        elemSRN = elemSRN.replace("/", "");
                        elemSRN = elemSRN.replace(".", "");
                        String arr[] = elemSRN.split(" ", 2);
                        elemSRN = arr[0];
                        VarenavnList.add(elemSRN);
                      
                    }
                }
   		// MEDICINRAADET I UDBUDSMATERIALE
                if (shSRN.getCell(colSRN, rowSRN).getContents().endsWith("MR")) {
                    int cMR = colSRN;
                    for (int y = 1; y < shSRN.getRows(); y++) {
                        Cell cellMR = shSRN.getCell(cMR, y);
                        ElementSRN[cMR][y] = cellMR.getContents();
                        String elemMR = cellMR.getContents();
                        MEDList.add(elemMR);
                        
                
                    }
                }

            }
        }
	
     	// FINDER LAEGEMIDLER SOM INDGAAR I MEDICINRAADET INDIKERET VED 1
        for (int v= 1; v < MEDList.size(); v++) {
            if(MEDList.get(v).equals("1")){
                 MRList.add(VarenavnList.get(v));
            }
        }
			// FJERNER GENTAGELSER I MRLIST
        for(int k = 0; k < MRList.size(); k++) {
            for(int j = k + 1; j < MRList.size(); j++) {
                if(MRList.get(k).equals(MRList.get(j))){
                    MRList.remove(j);
                    j--;
                }
            }
        }
			// LAEGEMIDDELNAVNE FOR SKIFT FRA AAR 2013 TIL 2014 TIL LOOK-A-LIKE
        int totalNoOfRows14 = shAMGROS14.getRows();
        int totalNoOfCols14 = shAMGROS14.getColumns();
        for (int row14 = 0; row14 < totalNoOfRows14; row14++) {
            for (int col14 = 0; col14 < totalNoOfCols14; col14++) {
               if (shAMGROS14.getCell(col14, row14).getContents().startsWith("Laegemiddel 2013")) {
                    int c13 = col14;
                    for (int y = 1; y < shAMGROS14.getRows(); y++) {
                        Cell cell13 = shAMGROS14.getCell(c13, y);
                        ElementAMGROS14[c13][y] = cell13.getContents();
                        String elem13 = cell13.getContents();
                        elem13 = elem13.substring(0).toLowerCase();
                        elem13 = elem13.replace(",", " ");
                        elem13 = elem13.replace(".", "");
                        elem13 = elem13.replace("/", "");
                        elem13 = elem13.replace("-", "");
                        String arr[] = elem13.split(" ", 2);
                        elem13 = arr[0];
                        NavnList.add(elem13);
                    }
               }
                if (shAMGROS14.getCell(col14, row14).getContents().startsWith("Laegemiddel 2014")) {
                    int c14 = col14;
                    for (int y = 1; y < shAMGROS14.getRows(); y++) {
                        Cell cell14 = shAMGROS14.getCell(c14, y);
                        ElementAMGROS14[c14][y] = cell14.getContents();
                        String elem14 = cell14.getContents();
                        elem14 = elem14.substring(0).toLowerCase();
                        elem14 = elem14.replace(",", " ");
                        elem14 = elem14.replace(".", "");
                        elem14 = elem14.replace("/", "");
                        elem14 = elem14.replace("-", "");
                        String arr[] = elem14.split(" ", 2);
                        elem14 = arr[0];
                        NavnList.add(elem14);  
                    }
                }
            }
        }
			// LAEGEMIDDELNAVNE FOR SKIFT FRA AAR 2014 TIL 2015 TIL LOOK-A-LIKE
        int totalNoOfRows15 = shAMGROS15.getRows();
        int totalNoOfCols15 = shAMGROS15.getColumns();
        for (int row15 = 0; row15 < totalNoOfRows15; row15++) {
            for (int col15 = 0; col15 < totalNoOfCols15; col15++) {
                if (shAMGROS15.getCell(col15, row15).getContents().startsWith("Laegemiddel 2014")) {
                    int c14 = col15;
                    for (int y = 1; y < shAMGROS15.getRows(); y++) {
                        Cell cell14 = shAMGROS15.getCell(c14, y); //Navn2017
                        ElementAMGROS15[c14][y] = cell14.getContents();
                        String elem14 = cell14.getContents();
                        elem14 = elem14.substring(0).toLowerCase();
                        elem14 = elem14.replace(",", " ");
                        elem14 = elem14.replace(".", "");
                        elem14 = elem14.replace("/", "");
                        elem14= elem14.replace("-", "");
                        String arr[] = elem14.split(" ", 2);
                        elem14 = arr[0];
                        NavnList.add(elem14); //laegemidler fra aar 2014 i skifteliste 2015 tilfoejet navnList
                    }
                }
                if (shAMGROS15.getCell(col15, row15).getContents().startsWith("Laegemiddel 2015")) {
                    int c15 = col15;
                    for (int y = 1; y < shAMGROS15.getRows(); y++) {
                        Cell cell15 = shAMGROS15.getCell(c15, y); //Navn2017
                        ElementAMGROS15[c15][y] = cell15.getContents();
                        String elem15 = cell15.getContents();
                        elem15 = elem15.substring(0).toLowerCase();
                        elem15 = elem15.replace(",", " ");
                        elem15 = elem15.replace(".", "");
                        elem15 = elem15.replace("/", "");
                        elem15= elem15.replace("-", "");
                        String arr[] = elem15.split(" ", 2);
                        elem15 = arr[0];
                        NavnList.add(elem15); //laegemidler fra aar 2015 i skifteliste 2015 tilfoejet navnList
                    }
                } 
            }
        }
			// LAEGEMIDDELNAVNE FOR SKIFT FRA AAR 2015 TIL 2016 TIL LOOK-A-LIKE
        int totalNoOfRows16 = shAMGROS16.getRows();
        int totalNoOfCols16 = shAMGROS16.getColumns();
        for (int row16 = 0; row16 < totalNoOfRows16; row16++) {
            for (int col16 = 0; col16 < totalNoOfCols16; col16++) {
                if (shAMGROS16.getCell(col16, row16).getContents().startsWith("Laegemiddel 2015")) {
                    int c15 = col16;
                    for (int y = 1; y < shAMGROS16.getRows(); y++) {
                        Cell cell15 = shAMGROS16.getCell(c15, y);
                        ElementAMGROS16[c15][y] = cell15.getContents();
                        String elem15 = cell15.getContents();
                        elem15 = elem15.substring(0).toLowerCase();
                        elem15 = elem15.replace(",", " ");
                        elem15 = elem15.replace(".", "");
                        elem15 = elem15.replace("/", "");
                        elem15 = elem15.replace("-", "");
                        String arr[] = elem15.split(" ", 2);
                        elem15 = arr[0];
                        NavnList.add(elem15); //laegemidler fra aar 2015 i skifteliste 2016 tilfoejet navnList
                    }
                }
                if (shAMGROS16.getCell(col16, row16).getContents().startsWith("Laegemiddel 2016")) {
                    int c16 = col16; 
                    for (int y = 1; y < shAMGROS16.getRows(); y++) {
                        Cell cell16 = shAMGROS16.getCell(c16, y);
                        ElementAMGROS16[c16][y] = cell16.getContents();
                        String elem16 = cell16.getContents();
                        elem16 = elem16.substring(0).toLowerCase();
                        elem16 = elem16.replace(",", " ");
                        elem16 = elem16.replace(".", "");
                        elem16 = elem16.replace("/", "");
                        elem16 = elem16.replace("-", "");
                        String arr[] = elem16.split(" ", 2);
                        elem16 = arr[0];
                        NavnList.add(elem16); //laegemidler fra aar 2016 i skifteliste 2016 tilfoejet navnList
                    }
                }   
            }
        }
        // LAEGEMIDDELNAVNE FOR SKIFT FRA AAR 2016 TIL 2017 TIL LOOK-A-LIKE
        int totalNoOfRows17 = shAMGROS17.getRows();
        int totalNoOfCols17 = shAMGROS17.getColumns();
        for (int row17 = 0; row17 < totalNoOfRows17; row17++) {
            for (int col17 = 0; col17 < totalNoOfCols17; col17++) {
                if (shAMGROS17.getCell(col17, row17).getContents().startsWith("Laegemiddel 2016")) {
                    int c16 = col17;
                    for (int y = 1; y < shAMGROS17.getRows(); y++) {
                        Cell cell16 = shAMGROS17.getCell(c16, y);
                        ElementAMGROS17[c16][y] = cell16.getContents();
                        String elem16 = cell16.getContents();
                        elem16 = elem16.substring(0).toLowerCase();
                        elem16 = elem16.replace(",", " ");
                        elem16 = elem16.replace(".", "");
                        elem16 = elem16.replace("/", "");
                        elem16 = elem16.replace("-", "");
                        String arr[] = elem16.split(" ", 2);
                        elem16 = arr[0];
                        NavnList.add(elem16); //laegemidler fra aar 2016 i skifteliste 2017 tilfoejet navnList
                    }
                }
                if (shAMGROS17.getCell(col17, row17).getContents().startsWith("Laegemiddel 2017")) {
                    int c17 = col17;
                    for (int y = 1; y < shAMGROS17.getRows(); y++) {
                        Cell cell17 = shAMGROS17.getCell(c17, y);
                        ElementAMGROS17[c17][y] = cell17.getContents();
                        String elem17 = cell17.getContents();
                        elem17 = elem17.substring(0).toLowerCase();
                        elem17 = elem17.replace(",", " ");
                        elem17 = elem17.replace(".", "");
                        elem17 = elem17.replace("/", "");
                        elem17 = elem17.replace("-", "");
                        String arr[] = elem17.split(" ", 2);
                        elem17 = arr[0];
                        NavnList.add(elem17); //laegemidler fra aar 2017 i skifteliste 2017 tilfoejet navnList
                    }
                }   
            }
        }
       // LAEGEMIDDELNAVNE FOR SKIFT FRA AAR 2017 TIL 2018 TIL LOOK-A-LIKE
       int totalNoOfRows18 = shAMGROS18.getRows();
       int totalNoOfCols18 = shAMGROS18.getColumns();
       for (int row18 = 0; row18 < totalNoOfRows18; row18++) {
            for (int col18 = 0; col18 < totalNoOfCols18; col18++) {
                 if (shAMGROS18.getCell(col18, row18).getContents().startsWith("Laegemiddel 2017")) {
                    int c17 = col18;
                    for (int y = 1; y < shAMGROS18.getRows(); y++) {
                        Cell cell17 = shAMGROS18.getCell(c17, y);
                        ElementAMGROS18[c17][y] = cell17.getContents();
                        String elem17 = cell17.getContents();
                        elem17 = elem17.substring(0).toLowerCase();
                        elem17 = elem17.replace(",", " ");
                        elem17 = elem17.replace(".", "");
                        elem17 = elem17.replace("/", "");
                        elem17= elem17.replace("-", "");
                        String arr[] = elem17.split(" ", 2);
                        elem17 = arr[0];
                        NavnList.add(elem17); //laegemidler fra aar 2017 i skifteliste 2018 tilfoejet navnList
                    }
                }
                if (shAMGROS18.getCell(col18, row18).getContents().startsWith("Laegemiddel 2018")) {
                    int c18 = col18;
                    for (int y = 1; y < shAMGROS18.getRows(); y++) {
                        Cell cell18 = shAMGROS18.getCell(c18, y);
                        ElementAMGROS18[c18][y] = cell18.getContents();
                        String elem18 = cell18.getContents();
                        elem18 = elem18.substring(0).toLowerCase();
                        elem18 = elem18.replace(",", " ");
                        elem18 = elem18.replace(".", "");
                        elem18 = elem18.replace("/", "");
                        elem18= elem18.replace("-", "");
                        String arr[] = elem18.split(" ", 2);
                        elem18 = arr[0];
                        NavnList.add(elem18); //laegemidler fra aar 2018 i skifteliste 2018 tilfoejet navnList
                    }
                }   
            }
        }
        
    	ArrayList<Integer> NavnColList = new ArrayList<>(); //ArrayList til laegemiddelnavne
    	ArrayList<Integer> DispColList = new ArrayList<>(); // ArrayList til dispenseringsforme
   	ArrayList<Integer> StyrkeColList = new ArrayList<>(); // ArrayList til styrke
	
	// FINDER KOLONNE HVOR LAEGEMIDDELNAVNE, DISPENSERINGSFORME, STYRKER OG ATC-KODE ER I INPUT EXCELARKET
        int totalNoOfRows = shAMGROS.getRows();
        int totalNoOfCols = shAMGROS.getColumns();
        for (int row = 0; row < totalNoOfRows; row++) {
            for (int col = 0; col < totalNoOfCols; col++) {
                if (shAMGROS.getCell(col, row).getContents().startsWith("Laegemiddel")) {
                    NavnColList.add(col);
                        for (int i=0; i < NavnColList.size(); i++) {
                            for(int j = i + 1; j < NavnColList.size(); j++) {
                                a = NavnColList.get(i);
                                b = NavnColList.get(j);
                               }
                        }
                } 
                if (shAMGROS.getCell(col, row).getContents().startsWith("Disp")) {
                    DispColList.add(col);
                        for (int i=0; i < DispColList.size(); i++) {
                            for(int j = i + 1; j < DispColList.size(); j++) {
                                c = DispColList.get(i);
                                d = DispColList.get(j);
                               }
                            }      
                }
                if (shAMGROS.getCell(col, row).getContents().startsWith("Styrke")) {
                    StyrkeColList.add(col);
                        for (int i=0; i < StyrkeColList.size(); i++) {
                            for(int j = i + 1; j < StyrkeColList.size(); j++) {
                                e = StyrkeColList.get(i);
                                f = StyrkeColList.get(j);
                               }
                            }      
                }
                if (shAMGROS.getCell(col, row).getContents().startsWith("ATC")) {
                    atc = col;
                }

            }
        }
	
        for (int t = 1; t < shAMGROS.getRows(); t++) {
            Cell cella = shAMGROS.getCell(a, t); //NavnBefore
            Cell cellb = shAMGROS.getCell(b, t); //Navn
            Cell cellc = shAMGROS.getCell(c, t); //DispBefore
            Cell celld = shAMGROS.getCell(d, t); //Disp
            Cell celle = shAMGROS.getCell(e, t); //StyrkeBefore
            Cell cellf = shAMGROS.getCell(f, t); //Styrke
            Cell cellatc = shAMGROS.getCell(atc, t); //ATCkode

            ElementAMGROS[a][t] = cella.getContents();
            ElementAMGROS[b][t] = cellb.getContents();
            ElementAMGROS[c][t] = cellc.getContents();
            ElementAMGROS[d][t] = celld.getContents();
            ElementAMGROS[e][t] = celle.getContents();
            ElementAMGROS[f][t] = cellf.getContents();
            ElementAMGROS[atc][t] = cellatc.getContents();

            String elema = cella.getContents();
            String elemb = cellb.getContents();
            String elemc = cellc.getContents();
            String elemd = celld.getContents();
            String eleme = celle.getContents();
            String elemf = cellf.getContents();
            String elematc = cellatc.getContents();

            // PRAEPROCESSEIRNG AF LAEGEMIDDELNAVNE:
            elema = elema.substring(0).toLowerCase();
            elemb = elemb.substring(0).toLowerCase();
            elema = elema.replaceAll("/", "");
            elemb = elemb.replaceAll("/", "");
            elema = elema.replaceAll(",", " ");
            elemb = elemb.replaceAll(",", " ");
            elema = elema.replaceAll("\\.", " ");
            elemb = elemb.replaceAll("\\.", " ");
            String arra[] = elema.split(" ", 2);
            elema = arra[0];
            String arrb[] = elemb.split(" ", 2);
            elemb = arrb[0];
            
            NavnList.add(elema); //laegemidler for aaret foer skiftet tilfoejet til NavnList

             
            // PRAEPROCESSEIRNG AF DISPENSERINGSFORME:
            elemc = elemc.substring(0).toLowerCase();
            elemd = elemd.substring(0).toLowerCase();
            
            elemc = elemc.replaceAll("filmovertrukne", "");
            elemc = elemc.replace("tabl.", "tabletter");
            elemc = elemc.replace("tab.", "tabletter");
            elemc = elemc.replaceFirst("\\W*", " ");
            elemc = elemc.replaceAll("/", " ");
            
            elemd = elemd.replaceAll("filmovertrukne", "");
            elemd = elemd.replace("tabl.", "tabletter");
            elemd = elemd.replace("tab.", "tabletter");
            elemd = elemd.replaceFirst("\\W*", " ");
            elemd = elemd.replaceAll("/", " ");

            elemc = elemc.replace("vsk,", "vaeske");
            elemc = elemc.replace("vsk.", "vaeske");
            elemc = elemc.replace("v.", "vaeske");
            elemc = elemc.replaceAll("vaeske", "");
            elemc = elemc.replaceAll("infusionsvaeske", "inf.");
            elemc = elemc.replace("inj", "injektionsvaeske ");
            elemc = elemc.replace("inj.", "injektionsvaeske ");
            elemc = elemc.replace("inf", "infusionsvaeske ");
            elemc = elemc.replace("inf.", "infusionsvaeske ");
            
            elemd = elemd.replace("vsk,", "vaeske");
            elemd = elemd.replace("vsk.", "vaeske");
            elemd = elemd.replace("v.", "vaeske");
            elemd = elemd.replaceAll("vaeske", "");
            elemd = elemd.replaceAll("infusionsvaeske", "inf.");
            elemd = elemd.replace("inj", "injektionsvaeske ");
            elemd = elemd.replace("inj.", "injektionsvaeske ");
            elemd = elemd.replace("inf", "infusionsvaeske ");
            elemd = elemd.replace("inf.", "infusionsvaeske ");

            elemc = elemc.replace("opl.", "oploesning");
            elemc = elemc.replaceAll("oploesningpl", "oploesning");
            elemc = elemc.replaceAll("oploesningp", "oploesning");
            if (elemc.endsWith("o")) {
                elemc = elemc.substring(0, elemc.length() - 1) + "oploesning";
            }
            if (elemc.endsWith("op")) {
                elemc = elemc.substring(0, elemc.length() - 2) + "oploesning";
            }
            if (elemc.endsWith("opl")) {
                elemc = elemc.substring(0, elemc.length() - 3) + "oploesning";
            }

          
            elemd = elemd.replace("opl.", "oploesning");
            elemd = elemd.replaceAll("oploesningpl", "oploesning");
            elemd = elemd.replaceAll("oploesningp", "oploesning");
            if (elemd.endsWith("o")) {
                elemd = elemd.substring(0, elemd.length() - 1) + "oploesning";
            }
            if (elemd.endsWith("op")) {
                elemd = elemd.substring(0, elemd.length() - 2) + "oploesning";
            }
            if (elemd.endsWith("opl")) {
                elemd = elemd.substring(0, elemd.length() - 3) + "oploesning";
            }

            elemc = elemc.replace("pulv", "pulver ");
            elemc = elemc.replace("pul", "pulver ");
            elemc = elemc.replaceAll("lver", "");
            elemc = elemc.replace("pu", "pulver ");
            
            elemd = elemd.replace("pulv", "pulver ");
            elemd = elemd.replace("pul", "pulver ");
            elemd = elemd.replaceAll("lver", "");
            elemd = elemd.replace("pu", "pulver ");

            elemc = elemc.replace("t.", "til ");
            elemc = elemc.replace("sol.", "solvens ");
            elemc = elemc.replace("konc.", "koncentrat ");
            
            elemd = elemd.replace("t.", "til ");
            elemd = elemd.replace("sol.", "solvens ");
            elemd = elemd.replace("konc.", "koncentrat ");

            elemc = elemc.replace(",", "  ");
            elemc = elemc.replace(".", "  ");
            elemc = elemc.replace("-", "og");
            elemc = elemc.replaceAll("\\s+", " ");

            elemd = elemd.replace(".", " ");
            elemd = elemd.replace(",", " ");
            elemd = elemd.replace("-", "og");
            elemd = elemd.replaceAll("\\s+", " ");
          
            // PRAEPROCESSEIRNG AF STYRKE:
            eleme = eleme.substring(0).toLowerCase();
            elemf = elemf.substring(0).toLowerCase();
            eleme = eleme.replaceAll("\\(", "").replaceAll("\\)","");
            elemf = elemf.replaceAll("\\(", "").replaceAll("\\)","");
           if ((eleme.equals("")) || (eleme.equals("tom")))  {
                eleme = eleme.replace(eleme, elemf);
            }
            eleme = eleme.replaceAll(" ", "");
            elemf = elemf.replaceAll(" ", "");
            
            eleme = eleme.replace("mikrog.", "mikrogram");
            elemf = elemf.replace("mikrog.", "mikrogram");
            eleme = eleme.replace("mikrog", "mikrogram");
            elemf = elemf.replace("mikrog", "mikrogram");
            eleme = eleme.replace("mikg", "mikrogram");
            elemf = elemf.replace("mikg", "mikrogram");
            eleme = eleme.replace("dosis", "dos");
            elemf = elemf.replace("dosis", "dos");
 
            String Navn = elemb;
            String NavnBefore = elema;       
            String Disp = elemd;
            String DispBefore = elemc;
            String Styrke = elemf;
            String StyrkeBefore = eleme;
            String ATC = elematc;

			// FJERNER GENTAGELSER I NAVNLIST
        for(int i = 2; i < NavnList.size(); i++) {
            for(int j = i + 1; j < NavnList.size(); j++) {
                if(NavnList.get(i).equals(NavnList.get(j))){
                    NavnList.remove(j);
                    j--; 
                }
            } 
        }

            //RISIKOVURDERING OG VAEGTNING
            double intet = 0.0;
            double navn = 1.0;
            double disp = 2.0;
            double look = 2.0;
            double styrke = 2.0;
            double atc = 3.0; // ATC-kritisk
            double risiko = 5.0; // Risikolaegemiddel 
            double mr = 5.0; // Medicinraadet
            double sum = intet+navn+disp+styrke+look+atc+risiko+mr; 
            int max = 4; // Max antal af distance af laegemidler

       Levenshtein classDistance = new Levenshtein();
       if ((Navn.equals(NavnBefore)) && (Disp.equals(DispBefore)) && (Styrke.equals(StyrkeBefore))) {
                nyScore = intet;
                nyStatus = "";
                    for (int j = 1; j < NavnList.size(); j++) {
                        if (classDistance.distance(NavnList.get(j), Navn) > 0 && classDistance.distance(NavnList.get(j), Navn) < max){
                            nyStatus = nyStatus + "Look-a-like:" + " " + NavnList.get(j) + "\n";
                                if(nyScore == intet){
                                    nyScore  = nyScore + look;
                                }
                        }
                    }
                    for (int i = 1; i < ATCkoderList.size(); i++) {
                        if (ATC.startsWith(ATCkoderList.get(i))){  
                            nyScore  = nyScore + atc;
                            nyStatus = nyStatus + "ATC-kritisk:" + " " + ATCkoderList.get(i) + "\n"; 
                        }
                    } 
                    for (int m = 1; m < RisikoList.size(); m++){
                        if (ATC.equals(RisikoList.get(m))) {
                            nyScore = nyScore + risiko;
                            nyStatus = nyStatus + "Risikolaegemiddel:" + " " + RisikoList.get(m) + "\n";
                        }
                    } 

                    for (int o=1; o < MRList.size(); o++) {
                        if ((Navn.equals(MRList.get(o)))) {     
                            nyScore = nyScore + mr;
                            nyStatus = nyStatus + "Medicinraadet";  
                        }
                    }
                score = (nyScore/sum)*100;
                Status = "Score:" + " " + score + " " + "%" + "\n" + nyStatus; 
           } else if ((!Navn.equals(NavnBefore)) && (Disp.equals(DispBefore)) && (Styrke.equals(StyrkeBefore))) {
                nyScore = navn;
                nyStatus = "Laegemiddelnavn aendret fra" + " " + NavnBefore + " " + "til" + " " + Navn + "\n";
                    for (int j = 1; j < NavnList.size(); j++) {
                        if (classDistance.distance(NavnList.get(j), Navn) > 0 && classDistance.distance(NavnList.get(j), Navn) < max){
                            nyStatus = nyStatus + "Look-a-like:" + " " + NavnList.get(j) + "\n";
                                if(nyScore == intet){
                                    nyScore  = nyScore + look;
                                }
                        }
                    }
                    for (int i = 1; i < ATCkoderList.size(); i++) {
                        if (ATC.startsWith(ATCkoderList.get(i))){  
                            nyScore  = nyScore + atc;
                            nyStatus = nyStatus + "ATC-kritisk:" + " " + ATCkoderList.get(i) + "\n"; 
                        }
                    } 
                    for (int m = 1; m < RisikoList.size(); m++){
                        if (ATC.equals(RisikoList.get(m))) {
                            nyScore = nyScore + risiko;
                            nyStatus = nyStatus + "Risikolaegemiddel:" + " " + RisikoList.get(m) + "\n";
                        }
                    } 

                   for (int o=1; o < MRList.size(); o++) {
                        if ((Navn.equals(MRList.get(o)))) {     
                            nyScore = nyScore + mr;
                            nyStatus = nyStatus + "Medicinraadet";  
                        }
                    }
                score = (nyScore/sum)*100;
                Status = "Score:" + " " + score + " " + "%" + "\n" + nyStatus; 
           } else if ((!Navn.equals(NavnBefore)) && (!Disp.equals(DispBefore)) && (Styrke.equals(StyrkeBefore))) {
                nyScore = navn+disp;
                nyStatus = "Laegemiddelnavn aendret fra" + " " + NavnBefore + " " + "til" + " " + Navn + "\n" + "Dispenseringsform aendret fra" + " " + DispBefore + " " +"til" + " " + Disp + "\n";
                   for (int j = 1; j < NavnList.size(); j++) {
                        if (classDistance.distance(NavnList.get(j), Navn) > 0 && classDistance.distance(NavnList.get(j), Navn) < max){
                            nyStatus = nyStatus + "Look-a-like:" + " " + NavnList.get(j) + "\n";
                                if(nyScore == intet){
                                    nyScore  = nyScore + look;
                                }
                        }
                    }
                    for (int i = 1; i < ATCkoderList.size(); i++) {
                        if (ATC.startsWith(ATCkoderList.get(i))){  
                            nyScore  = nyScore + atc;
                            nyStatus = nyStatus + "ATC-kritisk:" + " " + ATCkoderList.get(i) + "\n"; 
                        }
                    } 
                    for (int m = 1; m < RisikoList.size(); m++){
                        if (ATC.equals(RisikoList.get(m))) {
                            nyScore = nyScore + risiko;
                            nyStatus = nyStatus + "Risikolaegemiddel:" + " " + RisikoList.get(m) + "\n";
                        }
                    } 

                   for (int o=1; o < MRList.size(); o++) {
                        if ((Navn.equals(MRList.get(o)))) {     
                            nyScore = nyScore + mr;
                            nyStatus = nyStatus + "Medicinraadet";  
                        }
                    }
                score = (nyScore/sum)*100;
                Status = "Score:" + " " + score + " " + "%" + "\n" + nyStatus; 
            } else if ((!Navn.equals(NavnBefore)) && (Disp.equals(DispBefore)) && (!Styrke.equals(StyrkeBefore))) { 
                nyScore = navn+disp;
                nyStatus = "Laegemiddelnavn aendret fra" + " " + NavnBefore + " " + "til" + " " + Navn + "\n" + "Styrke aendret fra" + " " + StyrkeBefore + " " + "til" + " " + Styrke + "\n";
                    for (int j = 1; j < NavnList.size(); j++) {
                        if (classDistance.distance(NavnList.get(j), Navn) > 0 && classDistance.distance(NavnList.get(j), Navn) < max){
                            nyStatus = nyStatus + "Look-a-like:" + " " + NavnList.get(j) + "\n";
                                if(nyScore == intet){
                                    nyScore  = nyScore + look;
                                }
                        }
                    }
                    for (int i = 1; i < ATCkoderList.size(); i++) {
                        if (ATC.startsWith(ATCkoderList.get(i))){  
                            nyScore  = nyScore + atc;
                            nyStatus = nyStatus + "ATC-kritisk:" + " " + ATCkoderList.get(i) + "\n"; 
                        }
                    } 
                    for (int m = 1; m < RisikoList.size(); m++){
                        if (ATC.equals(RisikoList.get(m))) {
                            nyScore = nyScore + risiko;
                            nyStatus = nyStatus + "Risikolaegemiddel:" + " " + RisikoList.get(m) + "\n";
                        }
                    } 

                    for (int o=1; o < MRList.size(); o++) {
                        if ((Navn.equals(MRList.get(o)))) {     
                            nyScore = nyScore + mr;
                            nyStatus = nyStatus + "Medicinraadet";  
                        }
                    }
                score = (nyScore/sum)*100;
                Status = "Score:" + " " + score + " " + "%" + "\n" + nyStatus; 
            } else if ((!Navn.equals(NavnBefore)) && (!Disp.equals(DispBefore)) && (!Styrke.equals(StyrkeBefore))) {
                nyScore = navn+disp+styrke;
                nyStatus =  "Laegemiddelnavn aendret fra" + " " + NavnBefore + " " + "til" + " " + Navn + "\n" + "Dispenseringsform aendret fra" + " " + DispBefore + " " + "til" + " " + Disp + "\n" + "Styrke aendret fra" + " " + StyrkeBefore + " " + "til" + " " + Styrke + "\n";
                   for (int j = 1; j < NavnList.size(); j++) {
                        if (classDistance.distance(NavnList.get(j), Navn) > 0 && classDistance.distance(NavnList.get(j), Navn) < max){
                            nyStatus = nyStatus + "Look-a-like:" + " " + NavnList.get(j) + "\n";
                                if(nyScore == intet){
                                    nyScore  = nyScore + look;
                                }
                        }
                    }
                    for (int i = 1; i < ATCkoderList.size(); i++) {
                        if (ATC.startsWith(ATCkoderList.get(i))){  
                            nyScore  = nyScore + atc;
                            nyStatus = nyStatus + "ATC-kritisk:" + " " + ATCkoderList.get(i) + "\n"; 
                        }
                    } 
                    for (int m = 1; m < RisikoList.size(); m++){
                        if (ATC.equals(RisikoList.get(m))) {
                            nyScore = nyScore + risiko;
                            nyStatus = nyStatus + "Risikolaegemiddel:" + " " + RisikoList.get(m) + "\n";
                        }
                    } 

                   for (int o=1; o < MRList.size(); o++) {
                        if ((Navn.equals(MRList.get(o)))) {     
                            nyScore = nyScore + mr;
                            nyStatus = nyStatus + "Medicinraadet";  
                        }
                    }
                score = (nyScore/sum)*100;
                Status = "Score:" + " " + score + " " + "%" + "\n" + nyStatus; 
            } else if ((Navn.equals(NavnBefore)) && (!Disp.equals(DispBefore)) && (Styrke.equals(StyrkeBefore))) {   
                nyScore = disp;
                nyStatus = "Dispenseringsform aendret fra" + " " + DispBefore + "til" + " " + Disp + "\n";
                   for (int j = 1; j < NavnList.size(); j++) {
                        if (classDistance.distance(NavnList.get(j), Navn) > 0 && classDistance.distance(NavnList.get(j), Navn) < max){
                            nyStatus = nyStatus + "Look-a-like:" + " " + NavnList.get(j) + "\n";
                                if(nyScore == intet){
                                    nyScore  = nyScore + look;
                                }
                        }
                    }
                    for (int i = 1; i < ATCkoderList.size(); i++) {
                        if (ATC.startsWith(ATCkoderList.get(i))){  
                            nyScore  = nyScore + atc;
                            nyStatus = nyStatus + "ATC-kritisk:" + " " + ATCkoderList.get(i) + "\n"; 
                        }
                    } 
                    for (int m = 1; m < RisikoList.size(); m++){
                        if (ATC.equals(RisikoList.get(m))) {
                            nyScore = nyScore + risiko;
                            nyStatus = nyStatus + "Risikolaegemiddel:" + " " + RisikoList.get(m) + "\n";
                        }
                    } 

                   for (int o=1; o < MRList.size(); o++) {
                        if ((Navn.equals(MRList.get(o)))) {     
                            nyScore = nyScore + mr;
                            nyStatus = nyStatus + "Medicinraadet";  
                        }
                    }
                score = (nyScore/sum)*100;
                Status = "Score:" + " " + score + " " + "%" + "\n" + nyStatus; 
            }  else if ((Navn.equals(NavnBefore)) && (!Disp.equals(DispBefore)) && (!Styrke.equals(StyrkeBefore))) {
                nyScore = disp+styrke;
                nyStatus = "Dispenseringsform aendret fra" + " " + DispBefore + " " + "til" + " " + Disp + "\n" + "Styrke aendret fra" + " " + StyrkeBefore + " " + "til" + " " + Styrke + "\n";
                    for (int j = 1; j < NavnList.size(); j++) {
                        if (classDistance.distance(NavnList.get(j), Navn) > 0 && classDistance.distance(NavnList.get(j), Navn) < max){
                            nyStatus = nyStatus + "Look-a-like:" + " " + NavnList.get(j) + "\n";
                                if(nyScore == intet){
                                    nyScore  = nyScore + look;
                                }
                        }
                    }
                    for (int i = 1; i < ATCkoderList.size(); i++) {
                        if (ATC.startsWith(ATCkoderList.get(i))){  
                            nyScore  = nyScore + atc;
                            nyStatus = nyStatus + "ATC-kritisk:" + " " + ATCkoderList.get(i) + "\n"; 
                        }
                    } 
                    for (int m = 1; m < RisikoList.size(); m++){
                        if (ATC.equals(RisikoList.get(m))) {
                            nyScore = nyScore + risiko;
                            nyStatus = nyStatus + "Risikolaegemiddel:" + " " + RisikoList.get(m) + "\n";
                        }s
                    } 

                   for (int o=1; o < MRList.size(); o++) {
                        if ((Navn.equals(MRList.get(o)))) {     
                            nyScore = nyScore + mr;
                            nyStatus = nyStatus + "Medicinraadet";  
                        }
                    }
                score = (nyScore/sum)*100;
                Status = "Score:" + " " + score + " " + "%" + "\n" + nyStatus; 
            }  else if ((Navn.equals(NavnBefore)) && (Disp.equals(DispBefore)) && (!Styrke.equals(StyrkeBefore))) {
                nyScore = styrke;
                nyStatus = "Styrke aendret fra" + " " + StyrkeBefore + " " + "til" + " " + Styrke + "\n";
                  for (int j = 1; j < NavnList.size(); j++) {
                        if (classDistance.distance(NavnList.get(j), Navn) > 0 && classDistance.distance(NavnList.get(j), Navn) < max){
                            nyStatus = nyStatus + "Look-a-like:" + " " + NavnList.get(j) + "\n";
                                if(nyScore == intet){
                                    nyScore  = nyScore + look;
                                }
                        }
                    }
                    for (int i = 1; i < ATCkoderList.size(); i++) {
                        if (ATC.startsWith(ATCkoderList.get(i))){  
                            nyScore  = nyScore + atc;
                            nyStatus = nyStatus + "ATC-kritisk:" + " " + ATCkoderList.get(i) + "\n"; 
                        }
                    } 
                    for (int m = 1; m < RisikoList.size(); m++){
                        if (ATC.equals(RisikoList.get(m))) {
                            nyScore = nyScore + risiko;
                            nyStatus = nyStatus + "Risikolaegemiddel:" + " " + RisikoList.get(m) + "\n";
                        }
                    } 

                   for (int o=1; o < MRList.size(); o++) {
                        if ((Navn.equals(MRList.get(o)))) {     
                            nyScore = nyScore + mr;
                            nyStatus = nyStatus + "Medicinraadet";  
                        }
                    }
                score = (nyScore/sum)*100;
                Status = "Score:" + " " + score + " " + "%" + "\n" + nyStatus; 
            } else {
                Status = "Error";
            }  
             

            label = new Label(14, t, Status);
            ws.addCell(label);
    }
    
        ww.write();
        ww.close();
        wb.close();

    }
    
    }


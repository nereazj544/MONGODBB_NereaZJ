package ANTERIORES_CARPETAS.Consulta;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;

public class MongoDB {

    // private static final String dbStr = "IE";
    // private static final String colStr = "Jugadores";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 
        while (true) {
            System.err.println("\n Selecciona una opcion: ");
            System.out.println("1. Crear archivo JSON. (FUNCIONA)");
            System.out.println("2. Leer el archivo JSON y insertar la coleccion dentro del MongoDB. (NO SE SI FUNCIONA)");
            System.out.println("3. Insertar (FUNCIONA)");
            System.out.println("4. Borrar (NO FUNCIONA)");
            System.out.println("5. Actualizar (NO FUNCIONA) ");
            System.out.println("6. EXIT");

            int e = sc.nextInt();
            switch (e) {
                case 1:
                    CrearFicheroJson();
                    break;
                case 2:
                    leerFicheroGuardardatos();
                    break;
                case 3:
                    insertar(sc);
                    break;
                case 4:
                    borrar(sc);
                    break;
                case 5:
                    actualizar(sc);
                    break;
                case 6:
                    System.err.println("¡¡ADIOSSSSS!!");
                    System.exit(0);

                default:
                    System.err.println("ERROR");
            }
        }
    }

    // Crear JSON (FUNCIONA)
    // 1

    private static void CrearFicheroJson() {
        // MongoDB
        MongoClient mClient = new MongoClient();
        MongoDatabase database = mClient.getDatabase("IE");
        MongoCollection<Document> jugadoresCollection = database.getCollection("Jugadores");

        System.out.println("Inserta la linea + el nombre del archivo json");
        // String ruta = sc.nextLine();
        // String enter= sc.nextLine();

        File file = new File("src\\main\\java\\RECURSOS\\Jugadores-MONGODB.json");
        FileWriter fic;

        try {
            fic = new FileWriter(file);
            BufferedWriter f = new BufferedWriter(fic);

            System.err.println("------------");
            List<Document> c = jugadoresCollection.find().into(new ArrayList<Document>());

            for (int i = 0; i < c.size(); i++) {
                System.out.println("Elemnto: " + i + ", " + c.get(i).toString());
                f.write(c.get(i).toJson());
                f.newLine();
            }
            f.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        mClient.close();
    }

    // leer fichero
    // 2

    // Analiza el documento JSON y lo inserta en la coleccion de Jugadores
    private static void leerFicheroGuardardatos() {
        try (MongoClient mClient = new MongoClient();
                FileReader fr = new FileReader("src\\main\\java\\RECURSOS\\Jugadores-MONGODB.json");
                BufferedReader bf = new BufferedReader(fr)) {
            MongoDatabase database = mClient.getDatabase("IE");
            MongoCollection<Document> c = database.getCollection("Jugadores");

            String json;

            while ((json = bf.readLine()) != null) {
                System.out.println(json);
                Document dc = new Document(Document.parse(json));
                c.insertOne(dc);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Insertar datos
    // 3

    //Se le pide al user que inserte los datos
    private static void insertar(Scanner sc) {
        try (com.mongodb.client.MongoClient mClient = MongoClients.create()) {
            MongoDatabase database = mClient.getDatabase("IE");

            System.out.println("Inserte: Nombre, Nacionalidad, Posiciones, Elemento, Equipo. Por separado");
            String enter1 = sc.nextLine();
            System.out.println("Enter para continuar");
            

            String enter = sc.nextLine();

            System.out.print("Nombre: ");
            String nombre = sc.nextLine();
            
            System.out.print("ENTER");
            String e = sc.nextLine();
            
            System.out.print("Nacionalidad: ");
            String Nacionalidad = sc.nextLine();
            
            System.out.print("ENTER");
            String e1 = sc.nextLine();
            
            System.out.print("Posiciones: ");
            String Posiciones = sc.nextLine();
            
            System.out.print("ENTER");
            String e2 = sc.nextLine();
            
            System.out.print("Elemento: ");
            String Elemento = sc.nextLine();
            
            System.out.print("ENTER");
            String e3 = sc.nextLine();
            
            System.out.print("Equipo: ");
            String Equipo = sc.nextLine();
            
            System.out.print("ENTER");
            String e4 = sc.nextLine();

            Document d = new Document();
            d.put("nombre", nombre);
            d.put("nacionalidad", Nacionalidad);
            d.put("posiciones", Posiciones);
            d.put("elemento", Elemento);
            d.put("Equipo", Equipo);

            MongoCollection<Document> c = database.getCollection("Jugadores");
            c.insertOne(d);
            System.out.println("Insertado correctamente.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Borrar datos
    // 4
    // se le pide al user que escriba el id del jugador que desea borrar
    private static void borrar(Scanner sc) {
        try (com.mongodb.client.MongoClient mClient = MongoClients.create()) {
            MongoDatabase database = mClient.getDatabase("IE");
            MongoCollection<Document> c = database.getCollection("Jugadores");

            // System.out.println("Ingrese el id del jugador que vaya a borrar: ");
            // String dId = sc.nextLine();
            // c.deleteOne(new Document("_id", dId));
            // System.out.println("Jugador con el id: " + dId + " eliminado.");

            System.out.println("Ingrese el id del jugador que vaya a borrar: ");
            String dId = sc.nextLine();
            String enter = sc.nextLine();

            DeleteResult r = c.deleteOne(new Document("_id", dId));

            if (r.getDeletedCount() == 1) {
                System.out.println("Jugador con el id: " + dId + " eliminado.");

            } else {
                System.out.println("Error al borrar el jugador");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Actualizar
    // 5
    
    private static void actualizar(Scanner sc) {
        try (com.mongodb.client.MongoClient mClient = MongoClients.create()) {
            MongoDatabase database = mClient.getDatabase("IE");
            
            System.out.println("Ingrese el ID para actualizar al jugador: ");
            String dId = sc.nextLine();
            // String enter = sc.nextLine();
            
            // System.out.println("ENTER");
            String Update = sc.nextLine();
            Document updateDoc = new Document();

            System.out.println("Escribe el campo que quieras actualizar");
            // switch (Update) {
            //     case "Nombre":
            //         String nombre = sc.nextLine();
            //         updateDoc.put("$set", new Document("nombre", nombre));
            //         break;
            //         case "Nacionalidad":
            //         String nacionalidad = sc.nextLine();
            //         updateDoc.put("$set", new Document("nacionalidad", nacionalidad));
                    
            //         break;
            //         case "Posiciones":
            //         String Posiciones = sc.nextLine();
            //         updateDoc.put("$set", new Document("Posiciones", Posiciones));
                    
            //         break;
            //         case "Elemento":
            //         String Elemento = sc.nextLine();
            //         updateDoc.put("$set", new Document("Elemento", Elemento));
                    
            //         break;
            
            //     case "Equipo":
            //         String Equipo = sc.nextLine();
            //         updateDoc.put("$set", new Document("Equipo", Equipo));
            //         break;
            
            //     default:
            //     System.out.println("No valido");
            //         break;
            // }


            String nuevo = sc.nextLine();
            System.out.println("Ingresa el nuevo valor:");
            
            
            updateDoc.put("$set", new Document(Update, nuevo));
            
            MongoCollection<Document> c = database.getCollection("Jugadores");
            
            updateDoc.put("nuevo", nuevo);
            // c.insertOne(updateDoc);
            c.updateOne(new Document("_id", dId), updateDoc);
            System.out.println("Jugador actualizado.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


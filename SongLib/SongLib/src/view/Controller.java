package view;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;

public class Controller
{

    static SongList myList = new SongList();


}

/*
*   This method is to add the song to the SongList
*
*   !!!!!!!!! We need to find a way to get the:
*       NAME, ALBUM, YEAR, ARTIST from the TEXTFIELDS
*       ONLY IFF they click on add
*
* */
private static void addSong(String name, String Album, String Year, String Artist)
{
    myList.setSong(name, Album, Year, Artist);
}

/*
*   This is a method that would be activated if someone clicks on song from the list
*   then Clicks on delete.
* */
private static void removeSong(Songs songIn)
{
    if(myList.removeSong(songIn) == false)
    {
        //this should trigger an error popup on the GUI to let the USER know their STUPID AF!!!
    }
}

private static void editSong(Songs EditedSong, Songs OriginalSong)
{
    editSong(OriginalSong, EditedSong);
}

/*
*   This method is what will be used to store the ArrayList into an XML
*   This needs to be called on before the EXIT of the program to store the SONGLIST
*   to long term Memory.
*
*   ----------Accepts a ArrayList of Song objects--------------
*   */

public void createXML(ArrayList<Songs> songs)
{
    try {

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // root elements
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("songList");
        doc.appendChild(rootElement);




        for(int i = 0; i<songs.size();i++)
        {
            String nameIn = songs.get(i).getName();
            String artistIn = songs.get(i).getArtist();
            String albumIn = songs.get(i).getAlbum();
            String yearIn = songs.get(i).getYear();

            // staff elements
            Element song = doc.createElement("Song");
            rootElement.appendChild(song);

            // set attribute to staff element
            Attr attr = doc.createAttribute("id");
            //attr.setValue("1");
            attr.setValue(Integer.toString(i));
            song.setAttributeNode(attr);

            // shorten way
            // staff.setAttribute("id", "1");

            // firstname elements
            Element songname = doc.createElement("name");
            songname.appendChild(doc.createTextNode(nameIn));
            song.appendChild(songname);

            // lastname elements
            Element artist = doc.createElement("artist");
            artist.appendChild(doc.createTextNode(artistIn));
            song.appendChild(artist);

            // nickname elements
            Element album = doc.createElement("album");
            album.appendChild(doc.createTextNode(albumIn));
            song.appendChild(album);

            // salary elements
            Element year = doc.createElement("year");
            year.appendChild(doc.createTextNode(yearIn));
            song.appendChild(year);
        }
        // write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("file.xml"));

        // Output to console for testing
        // StreamResult result = new StreamResult(System.out);

        transformer.transform(source, result);

        System.out.println("File saved!");

    } catch (ParserConfigurationException pce) {
        pce.printStackTrace();
    } catch (TransformerException tfe) {
        tfe.printStackTrace();
    }
}



public SongList getXML()
{
    try {

        File fXmlFile = new File("file.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);

        //optional, but recommended
        //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
        doc.getDocumentElement().normalize();

        System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

        NodeList nList = doc.getElementsByTagName("Songs");

        System.out.println("----------------------------");

        SongList songsFromXML = new SongList();
        String name, artist, album, year;
        for (int temp = 0; temp < nList.getLength(); temp++) {

            Node nNode = nList.item(temp);

            System.out.println("\nCurrent Element :" + nNode.getNodeName());

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                Element eElement = (Element) nNode;

                //^^^^^^^^^^^^^^^^^^^^ This is for debug ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^//
                System.out.println("Song id : " + eElement.getAttribute("id"));
                System.out.println("Name : " + eElement.getElementsByTagName("name").item(0).getTextContent());
                System.out.println("Artist : " + eElement.getElementsByTagName("artist").item(0).getTextContent());
                System.out.println("Album : " + eElement.getElementsByTagName("album").item(0).getTextContent());
                System.out.println("Year : " + eElement.getElementsByTagName("year").item(0).getTextContent());

                //^^^^^^^^^^^^^^^^^^^^ This is for debug ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^//

                name = eElement.getElementsByTagName("name").item(0).getTextContent();
                artist = eElement.getElementsByTagName("artist").item(0).getTextContent();
                album = eElement.getElementsByTagName("album").item(0).getTextContent();
                year = eElement.getElementsByTagName("year").item(0).getTextContent()

                songsFromXML.setSong(name, artist, album, year);
            }
        }

        return songsFromXML;
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
    return null;
}

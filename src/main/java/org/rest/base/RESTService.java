package org.rest.base;

import java.io.IOException;
import java.io.StringReader;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.gsstrozi.model.Artista;
import org.gsstrozi.model.Track;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Path("/")
public class RESTService {

	@GET
	@Path("/")
	public Response getHome() {
		
		String responseRet = "BEM VINDO ! <br><br> PARA BUSCAR POR ALGUM ARTISTA ACESSE: localhost:8081/consultas/nomeartista <br> <br> PARA BUSCAR POR ALGUMA MUSICA (TRACK) ACESSE: localhost:8081/consultas/nomeartista/nomemusica";
		
		return Response.ok(responseRet, MediaType.TEXT_HTML).build();
	}
	
	@GET
	@Path("/consultas/{artista}/")
	public Response getArtista(@PathParam("artista")String artistaNome) {
		
		Client client = Client.create();
		
		WebResource webResource = client.resource(getURLBuilder(artistaNome, null));
		
		ClientResponse response = webResource.accept("application/xml").post(ClientResponse.class);

		String responseRet = comporRetorno(response, true);
		if (responseRet == null)
			responseRet = "NAO ENCONTRADO INFORMACOES PARA O ARTISTA PROCURADO";
		
		return Response.ok(responseRet, MediaType.TEXT_HTML).build();

	}
	
	@GET
	@Path("/consultas/{artista}/{track}/")
	public Response getTrack(@PathParam("artista")String artistaNome, @PathParam("track")String track) {

		Client client = Client.create();
		
		WebResource webResource = client.resource(getURLBuilder(artistaNome, track));
		
		ClientResponse response = webResource.accept("application/xml").post(ClientResponse.class);

		String responseRet = comporRetorno(response, false);
		if (responseRet == null)
			responseRet = "NAO ENCONTRADO INFORMACOES PARA O ARTISTA /FAIXA (TRACK) PROCURADO";
		
		return Response.ok(responseRet, MediaType.TEXT_HTML).build();	
	}
	
	/**
	 * Constroi a URL para Consumir o Web Service
	 * @param artist
	 * @param track
	 * @param returnJSON
	 * @return
	 */
	private String getURLBuilder (String artist, String track) {
		
		StringBuffer URL = new StringBuffer("http://ws.audioscrobbler.com/2.0/");

		artist = artist.replace(" ", "%20");

		if (track != null && !track.isEmpty()) {
			track = track.replace(" ", "%20");
			URL.append("?method=track.getInfo&artist=").append(artist).append("&track=").append(track);
		}
		else {
			URL.append("?method=artist.getinfo&artist=").append(artist);
		}
		URL.append("&api_key=af8fe1d027258439b80f78481556e942");
		
		return URL.toString();
	}
	
	/**
	 * Valida e Cria a Mensagem de Retorno - Web Service Lastfm
	 * @param resp
	 * @return
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public String comporRetorno (ClientResponse resp, boolean apenasArtista) {
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		Document doc = null;
		
		try 
		{
			dBuilder = dbFactory.newDocumentBuilder();
		    InputSource is = new InputSource(new StringReader(resp.getEntity(String.class).trim()));
			doc = dBuilder.parse(is);
		} 
		catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		} catch (SAXException e2) {
			e2.printStackTrace();
		} catch (IOException e3) {
			e3.printStackTrace();
		}
		
		if (doc != null) {
			
			Element element = doc.getDocumentElement();
			
			String status = element.getAttribute("status");
			
			if (status == null || status.isEmpty() || status.equalsIgnoreCase("failed"))
				return null;
			
			NodeList nodeList = element.getChildNodes();
			
			for (int i = 0; i < nodeList.getLength(); i++) {

				Node node = nodeList.item(i);
				
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					
					Element elem = (Element) node;
					
					if (apenasArtista)
					{
						Artista artista = new Artista();
						
						artista.setNome(elem.getElementsByTagName("name").item(0) != null ? elem.getElementsByTagName("name").item(0).getTextContent() : "");
						artista.setResumo(elem.getElementsByTagName("summary").item(0).getTextContent() != null ? elem.getElementsByTagName("summary").item(0).getTextContent() : "");
						artista.setConteudo(elem.getElementsByTagName("content").item(0) != null ? elem.getElementsByTagName("content").item(0).getTextContent() : "");
						
						return artista.toString();
					}
					else
					{
						Track track = new Track();
						
						track.setNome(elem.getElementsByTagName("name").item(0) != null ? elem.getElementsByTagName("name").item(0).getTextContent() : "");
						track.setArtista(elem.getElementsByTagName("artist").item(0) != null ? elem.getElementsByTagName("artist").item(0).getTextContent() : "");
						track.setAlbum(elem.getElementsByTagName("album").item(0) != null ? elem.getElementsByTagName("album").item(0).getTextContent() : "");
						track.setPublicacao(elem.getElementsByTagName("published").item(0) != null ? elem.getElementsByTagName("published").item(0).getTextContent() : "");
						track.setResumo(elem.getElementsByTagName("summary").item(0) != null ? elem.getElementsByTagName("summary").item(0).getTextContent() : "");
						track.setConteudo(elem.getElementsByTagName("content").item(0) != null ? elem.getElementsByTagName("content").item(0).getTextContent() : "");
						
						return track.toString();	
					}
				}

			}
		}
		
		return null;
		
	}
}
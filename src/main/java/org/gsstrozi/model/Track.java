package org.gsstrozi.model;

public class Track {

	private String nome;
	private String artista;
	private String album;
	private String resumo;
	private String conteudo;
	private String publicacao;

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getResumo() {
		return resumo;
	}
	public void setResumo(String resumo) {
		this.resumo = resumo;
	}
	public String getConteudo() {
		return conteudo;
	}
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	
	public String getArtista() {
		return artista;
	}
	public void setArtista(String artista) {
		this.artista = artista;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public String getPublicacao() {
		return publicacao;
	}
	public void setPublicacao(String publicacao) {
		this.publicacao = publicacao;
	}
	@Override
    public String toString() {

		StringBuilder stringBuilder = new StringBuilder("<table border=2 style='width:100%'>");
        stringBuilder.append("<tr>");
        stringBuilder.append("<td> MUSICA : </td> <td>" + this.nome.toUpperCase() + " </td> ");
        stringBuilder.append("</tr>");
        stringBuilder.append("<tr>");
        stringBuilder.append("<td> ARTISTA : </td> <td>" + this.artista.toUpperCase() + " </td> ");
        stringBuilder.append("</tr>");
        stringBuilder.append("<tr>");
        stringBuilder.append("<td> ALBUM : </td> <td>" + this.album.toUpperCase() + " </td> ");
        stringBuilder.append("</tr>");
        stringBuilder.append("<tr>");
        stringBuilder.append("<td> RESUMO : </td> <td>" + this.resumo.toUpperCase() + " </td> ");
        stringBuilder.append("</tr>");
        stringBuilder.append("<tr>");
        stringBuilder.append("<td> CONTEUDO : </td> <td>" + this.conteudo.toUpperCase() + " </td> ");
        stringBuilder.append("</tr>");
        stringBuilder.append("</table>");
        
        return stringBuilder.toString();
    }
}

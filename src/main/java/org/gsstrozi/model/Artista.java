package org.gsstrozi.model;

public class Artista {

	private String nome;
	private String resumo;
	private String conteudo;
	
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
	
	@Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder("<table border=2 style='width:100%'>");
        stringBuilder.append("<tr>");
        stringBuilder.append("<td> NOME : </td> <td>" + this.nome.toUpperCase() + " </td> ");
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

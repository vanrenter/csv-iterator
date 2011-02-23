/*
    Copyright (C) 2006-2011  le.jhe@freesbee.fr

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.

 */
package org.jhe.csviterator;

import java.io.*;
import java.util.*;

/**
 * @author le.jhe@freesbee.fr
 * @license GPL v3
 */
public interface CSVIterator extends Iterator {

	/**
	 * Wrapper pour les messages de debug.
	 * @param e	Une exception &agrave; logger.
	 **/
	//FIXME
	//void debug(String methodName,Exception e);

	/**
	 * Wrapper pour les messages de debug.
	 * @param e	Une String &agrave; logger.
	 **/
	//FIXME
	//void debug(String methodName,String message);

	/**
	 * Permet de v&eacute;rifier le bon d&eacute;roulement des op&eacute;rations.
	 * @return	<tt>true</tt> si toutes les op&eacute;rations de lecture du fichier se sont bien pass&eacute;es.
	 **/
	public boolean getStatus();

	/**
	 * Permet d'initialiser le d&eacute;limiteur de champs.
	 * @param	delimiter la cha&icirc;ne utilis&eacute;e
	 */
	public void setFieldDelimiter(String delimiter);

	/**
	 * Permet d'initialiser le d&eacute;limiteur de valeurs &agrave; l'int&eacute;rieur d'un champ donn&eacute;.
	 * @param	valueDelimiter la cha&icirc;ne utilis&eacute;e
	 */
	public void setValueDelimiter(String valueDelimiter);

	/**
	 * Initialise le Reader et lit la liste des attributs (headers) du fichier CSV. L'impl&eacute;cute repose sur un init tardif
	 * se trouvant dans {@see #hasNext()}
	 * @param	filePath			Le chemin vers le fichier des mouvements ANNA.
	 * @throws	UnsupportedOperationException	Si la propri&eacute;t&eacute; <tt>ANNA_file</tt> n'est pas d&eacute;finie.
	 * @throws	UnsupportedOperationException	Si le fichier ne contient pas d'ent&ecirc;te
	 **/
	void init(String filePath) throws Exception;

	/**
	 * R&eacute;cup&egrave;re la liste des attributs (headers) du fichier CSV. Il s'agit de la premi&egrave;re ligne
	 * du fichier. Le s&eacute;parateur est d&eacute;finit dans la fichier de propri&eacute;t&eacute;.
	 * @see		ANNA_delimiter			La propri&eacute;t&eacute; d&eacute;finissant le s&eacute;parateur
	 * @param	filePath			Le chemin vers le fichier des mouvements ANNA.
	 * @throws	IOException			En cas d'erreur de lecture du fichier.
	 **/
	public List<String> getHeaders() throws IOException;

	/**
	 * Lit la ligne suivante dans le fichier.
	 * @return					Une <tt>Map</tt> contenant sous la forme <tt>nom=valeur</tt>
	 * @throws	IOException			En cas d'erreur de lecture du fichier.
	 * @throws	IOException			Si le nombre de valeur ne correspond pas aux ent&ecirc;tes.
	 **/
	public Map nextRecord() throws IOException;

	/**
	 * Ferme le fichier et v&eacute;rifie la conformit&eacute; des op&eacute;rations. Le fichier original est renomm&eacute;
	 * (pour le format voir la propri&eacute;t&eacute; <tt>ANNA_history_pattern</tt> ). La m&eacute;thode transfert &eacute;galement
	 * un fichier par FTP pour annoncer la fin de l'op&eacute;ration.
	 * @throws	IOException			En cas d'erreur du transfert FTP
	 * @throws	IOException			Si le nombre de valeur ne correspond pas aux ent&ecirc;tes.
	 */
	public void close() throws IOException;

	/**
	 * Permet de v&eacute;rifier la disponibilit&eacute; d'autres enregistements.
	 * @return	true				Si il y a encore des enregistrement
	 */
	public boolean hasNext();
}

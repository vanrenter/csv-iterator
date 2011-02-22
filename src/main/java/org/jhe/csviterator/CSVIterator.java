/*
 * @author le.jhe@freesbee.fr
 * License : CDDL v1.0 : http://www.sun.com/cddl/cddl.html
 **/

package org.jhe.csviterator;

import java.io.*;
import java.util.*;

/**
 * Adaptateur pour la lecture des fichiers de mouvement CSV.
 * @author le.jhe@freesbee.fr
 * @see com.waveset.util.FlatFileIterator
 **/
public class CSVIterator implements FlatFileIterator {

	private BufferedReader inputStream;
	private List<String> fieldNames;
	private HashMap<String,String> nextRecord;
	private String fieldDelimeter   = null;
	private String valueDelimeter   = null;
	private String annaFilePath     = null;
	private boolean allOK           = true;
	private boolean noCloseNeeded   = false;
	private boolean isInited        = false;

	private static final String CLASS = "com.sun.france.cs.waveset.util.CSVFlatFileIterator";

	/**
	 * Cr&eacute;&eacute; une instance de <tt>CSVIterator</tt>. Ce constructeur est utilis&eacute; pour les tests.
	 * @throws Exception	si une erreur se produit dans la m&eacute;thode {@see init()}
	 * @deprecated		Utiliser le constructeur avec argument
	 **/
	public CSVIterator()  throws Exception  {
		String methodName	= "CSVIterator()";
		//Hardcoded for test purposes
		debug(methodName,"FIXME : Harcoded file path for tests only in");
		init("data/CSV.TXT");
		isInited = true;
	}

	/**
	 * Cr&eacute;&eacute; une instance de <tt>CSVIterator</tt>.
	 * @throws Exception	si une erreur se produit dans la m&eacute;thode {@see init()}
	 * @param filePath	Le chemin vers le fichier CSV. Utilis&eacute; pour les tests.
	 **/
	public CSVIterator(String filePath, String fieldDelimeter, String valueDelimeter)  throws Exception  {
		String methodName	= "CSVIterator";

		this.annaFilePath	= filePath;
		this.fieldDelimeter	= fieldDelimeter;
		this.valueDelimeter	= valueDelimeter;
		isInited = false;
	}

	/**
	 * Cr&eacute;&eacute; une instance de <tt>CSVIterator</tt>.
	 * @throws Exception	si une erreur se produit dans la m&eacute;thode {@see init()}
	 * @param filePath	Le chemin vers le fichier CSV. Utilis&eacute; pour les tests.
	 **/
	public CSVIterator(String filePath)  throws Exception  {
		String methodName	= "CSVIterator";

		this.annaFilePath   = filePath;
		// switching to lazy init
		//init(filePath);
		isInited = false;
	}

	/**
	 * Wrapper pour les messages de debug.
	 * @param e	Une exception &agrave; logger.
	 * @deprecated	Utilisation de debug()
	 **/
	public static void debug(String methodName,Exception e)  {
		System.out.println(methodName+"() :"+e);
		e.printStackTrace(System.out);
	}

	/**
	 * Wrapper pour les messages de debug.
	 * @param e	Une String &agrave; logger.
	 * @deprecated	Utilisation de debug()
	 **/
	public static void debug(String methodName,String message)  {
		System.out.println(methodName+"() :"+message);
	}

	/**
	 * Permet de v&eacute;rifier le bon d&eacute;roulement des op&eacute;rations.
	 * @return	<tt>true</tt> si toutes les op&eacute;rations de lecture du fichier se sont bien pass&eacute;es.
	 **/
	public boolean getStatus() {
		return allOK;
	}

	/**
	 * Permet d'initialiser le d&eacute;limiteur de champs.
	 * @param	delimiter la cha&icirc;ne utilis&eacute;e
	 */
	public void setFieldDelimiter(String delimiter) {
		fieldDelimeter = delimiter;
	}

	/**
	 * Permet d'initialiser le d&eacute;limiteur de valeurs &agrave; l'int&eacute;rieur d'un champ donn&eacute;.
	 * @param	valueDelimiter la cha&icirc;ne utilis&eacute;e
	 */
	public void setValueDelimiter(String valueDelimiter) {
		valueDelimeter = valueDelimiter;
	}


	/**
	 * Initialise le Reader et lit la liste des attributs (headers) du fichier CSV. L'impl&eacute;cute repose sur un init tardif
	 * se trouvant dans {@see #hasNext()}
	 * @param	filePath			Le chemin vers le fichier des mouvements CSV.
	 * @param	fieldDelimeter			Le délimiteur de champs
	 * @param	valueDelimeter			Le délimiteur de valeurs
	 * @throws	UnsupportedOperationException	Si la propri&eacute;t&eacute; <tt>CSV_file</tt> n'est pas d&eacute;finie.
	 * @throws	UnsupportedOperationException	Si le fichier ne contient pas d'ent&ecirc;te
	 **/
	public void init(String filePath, String fieldDelimeter, String valueDelimeter) throws Exception {
		String methodName	= "init";

		init(filePath);

		setFieldDelimiter(fieldDelimeter);
		setFieldDelimiter(valueDelimeter);
	}
	/**
	 * Initialise le Reader et lit la liste des attributs (headers) du fichier CSV. L'impl&eacute;cute repose sur un init tardif
	 * se trouvant dans {@see #hasNext()}
	 * @param	filePath			Le chemin vers le fichier des mouvements CSV.
	 * @throws	UnsupportedOperationException	Si la propri&eacute;t&eacute; <tt>CSV_file</tt> n'est pas d&eacute;finie.
	 * @throws	UnsupportedOperationException	Si le fichier ne contient pas d'ent&ecirc;te
	 **/
	public void init(String filePath) throws Exception {
		String methodName	= "init";
		debug(methodName,"init File path: "+filePath);
		debug(methodName,"delimiter: "+fieldDelimeter);

		/** Creation de la Table des attributs **/
		fieldNames = new ArrayList();

		/** Ouverture du fichier **/
		if(filePath != null && filePath.length() > 0) {
			FileInputStream fileinputstream = new FileInputStream(filePath);
			inputStream = new BufferedReader(new InputStreamReader(fileinputstream));
		} else {
			allOK = false;
			throw new UnsupportedOperationException("CSV file ("+filePath+") can't be found");
		}

		try {
			// getting headers definition
			if (hasNext()){
				Map header = getHeader();
			} else  {
				// the file is empty, ignore it
				allOK = true;
				noCloseNeeded=true;
				//throw new UnsupportedOperationException("CSV_file does not contains any header");
			}

		} catch (Exception e) {
			debug(methodName,e);
		}
	}

	/**
	 * R&eacute;cup&egrave;re la liste des attributs (headers) du fichier CSV. Il s'agit de la premi&egrave;re ligne
	 * du fichier. Le s&eacute;parateur est d&eacute;finit dans la fichier de propri&eacute;t&eacute;.
	 * @see		CSV_delimiter			La propri&eacute;t&eacute; d&eacute;finissant le s&eacute;parateur
	 * @param	filePath			Le chemin vers le fichier des mouvements CSV.
	 * @throws	IOException			En cas d'erreur de lecture du fichier.
	 **/
	public Map getHeader() throws IOException {
		String methodName	= "getHeader";

		TreeMap header = new TreeMap();
		String fileLine = null;

		if((fileLine = inputStream.readLine()) != null) {
			StringTokenizer strTok = new StringTokenizer(fileLine,fieldDelimeter);
			while (strTok.hasMoreTokens()) {
				fieldNames.add(strTok.nextToken());
			}
		}
		debug(methodName,"fieldNames: "+fieldNames);

		return header;
	}

	/**
	 * Lit la ligne suivante dans le fichier.
	 * @return					Une <tt>Map</tt> contenant sous la forme <tt>nom=valeur</tt>
	 * @throws	IOException			En cas d'erreur de lecture du fichier.
	 * @throws	IOException			Si le nombre de valeur ne correspond pas aux ent&ecirc;tes.
	 **/
	public Map nextRecord() throws IOException {
		String methodName	= "nextRecord";

		TreeMap record = new TreeMap();
		int fieldCounter = 0;
		String fileLine = null;

		try {
			if((fileLine = inputStream.readLine()) != null && !fileLine.equals("")) {
				StringTokenizer strTok = new StringTokenizer(fileLine,fieldDelimeter,true);
				String aValue = null;
				String aName = null;
				// <FIXED>
				// split discards end of line with separators only
				// adding a fake value to the end of the line to avoid this
				fileLine+=fieldDelimeter+"NOT_A_VALUE";

				String[] values = fileLine.split(fieldDelimeter);
				// -1 is for "NOT_A_VALUE"                   --v
				for (fieldCounter=0; fieldCounter<values.length-1;fieldCounter++) {
					// </FIXED>
					aValue = values[fieldCounter];
					aName  = fieldNames.get(fieldCounter).toString();
					record.put(aName,aValue);
				}
				// not enough value in this record
				if (fieldCounter < fieldNames.size()-1 ) {
					throw new UnsupportedOperationException("Not enough value in record");
				}

			}
			//debug(methodName,record.toString());
			// too much values in this record
		} catch (UnsupportedOperationException UOex) {
			allOK = false;
			debug(methodName,"The following record has not enough values : "+fileLine);
			debug(methodName,UOex);
			//throw new IOException();
		} catch (IndexOutOfBoundsException indexEx) {
			allOK = false;
			debug(methodName,"The following record has too much values : "+fileLine);
			debug(methodName,indexEx);
			//throw new IOException();
		} catch (Exception ex) {
			allOK = false;
			debug(methodName,ex);
			//throw new IOException();
		}

		return record;
	}

	/**
	 * Ferme le fichier et v&eacute;rifie la conformit&eacute; des op&eacute;rations. Le fichier original est renomm&eacute;
	 * (pour le format voir la propri&eacute;t&eacute; <tt>CSV_history_pattern</tt> ). La m&eacute;thode transfert &eacute;galement
	 * un fichier par FTP pour annoncer la fin de l'op&eacute;ration.
	 * @throws	IOException			En cas d'erreur du transfert FTP
	 * @throws	IOException			Si le nombre de valeur ne correspond pas aux ent&ecirc;tes.
	 */
	public void close() throws IOException {
		if(inputStream != null) {
			inputStream.close();
		}
		// if the file was empty, do nothing
		if (noCloseNeeded)
			return;
		// empty string is the content of the file to transfer
		//doFtpTransfer();
	}

	/**
	 * Permet de v&eacute;rifier la disponibilit&eacute; d'autres enregistements.
	 * @return	true				Si il y a encore des enregistrement
	 */
	public boolean hasNext() {
		String methodName	= "hasNext";

		boolean result = false;

		if (!isInited) {

			try {
				isInited = true;
				debug(methodName,"annaFilePath="+annaFilePath);
				init(annaFilePath);
			} catch (Exception ex) {
				debug(methodName,"could not init() Iterartor");
				debug(methodName,ex);
				// if error during init(), consider not inited:
				isInited = false;

				return result;
			}
		}

		try {
			result = inputStream.ready();
		} catch (IOException ioe){
			debug(methodName,"CSVIterator : "+ioe);
			debug(methodName,ioe);
		}

		return result;
	}

	/**
	 * Utili&eacute; pour les tests.
	 **/
	public static void main(String args[]) {
		String methodName	= "main";

		try {
			String path = "data/CSV.TXT";
			CSVIterator iterator = new CSVIterator(path);
			//iterator.init(path);
			iterator.setFieldDelimiter("###");
			while (iterator.hasNext()) {
				Map record = iterator.nextRecord();
				debug(methodName,"new Record : "+record);
			}

			debug(methodName,"everything went OK ? "+iterator.getStatus());
			iterator.close();
		} catch (IOException ex) {
			debug(methodName,ex);
		} catch (Exception ex) {
			debug(methodName,ex);
		}
	}

	public Object next() {
		// TODO Auto-generated method stub
		return null;
	}

	public void remove() {
		// TODO Auto-generated method stub
		
	}
}

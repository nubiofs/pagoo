package br.com.infosolo.comum.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;

import org.apache.commons.io.IOUtils;

/**
 * 
 * <p>
 * Utilitario para manipular arquivos e fluxos de arquivos.
 * </p>
 * 
 */
public class ArquivoUtil implements Serializable {
	private static final long serialVersionUID = 1227314921804015225L;

	private static Logger logger = new Logger(ArquivoUtil.class.getName());
	
	private static int DEFAULT_CHUNK_SIZE = 512;

	/**
	 * <p>
	 * Transforma um array de bytes em um arquivo.
	 * </p>
	 * 
	 * @param pathName - Caminho do arquivo para onde os bytes serão escritos.
	 * @param bytes - Bytes a serem copiados.
	 * 
	 * @return Objeto File com o conteúdo sendo o dos bytes
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws NullPointerException Caso <code>pathName</code> ou <code>bytes</code> sejam <code>null</code>.
	 * @throws IllegalArgumentException Caso <code>pathName</code> seja vazio ou contenha apenas espaços em branco
	 * 
	 * @since 0.2
	 */
	public static File bytes2File(String pathName, byte[] bytes) throws FileNotFoundException, IOException {
		File file = null;
		
		TextoUtil.checkNotBlank(pathName);
		TextoUtil.checkNotNull(bytes);

		file = new File(pathName);

		OutputStream out = new FileOutputStream(file);

		out.write(bytes);
		out.flush();
		out.close();

		return file;
	}

	/**
	 * <p>
	 * Transforma um array de bytes em um <code>ByteArrayOutputStream</code>.
	 * </p>
	 * 
	 * @param bytes - Bytes que serão escritos no objeto ByteArrayOutputStream
	 * 
	 * @return ByteArrayOutputStream ou null
	 * 
	 * @throws IOException
	 * @throws NullPointerException Caso <code>bytes</code> sejam <code>null</code>.
	 * 
	 * @since 0.2
	 */
	public static ByteArrayOutputStream bytes2Stream(byte[] bytes) throws IOException {
		ByteArrayOutputStream byteOut = null;
		
		TextoUtil.checkNotNull(bytes);

		byteOut = new ByteArrayOutputStream();
		byteOut.write(bytes);

		return byteOut;
	}
	

	/*
	 * Utility method to write a given text to a file
	 */
	public boolean writeToFile(String fileName, String dataLine,
			boolean isAppendMode, boolean isNewLine) {

	    DataOutputStream dos;
	    if (isNewLine) {
			dataLine = "\n" + dataLine;
		}

		try {
			File outFile = new File(fileName);
			if (isAppendMode) {
				dos = new DataOutputStream(new FileOutputStream(fileName, true));
			} else {
				dos = new DataOutputStream(new FileOutputStream(outFile));
			}

			dos.writeBytes(dataLine);
			dos.close();
		} catch (FileNotFoundException ex) {
			return (false);
		} catch (IOException ex) {
			return (false);
		}
		return (true);
	}

	/*
	 * Reads data from a given file
	 */
	public String readFromFile(String fileName) {
		String DataLine = "";

		try {
			File inFile = new File(fileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inFile)));

			DataLine = br.readLine();
			br.close();
		} catch (FileNotFoundException ex) {
			return (null);
		} catch (IOException ex) {
			return (null);
		}
		return (DataLine);
	}

	/**
	 * Verifica se o arquivo existe no disco.
	 * @param fileName
	 * @return
	 */
	public static boolean isFileExists(String fileName) {
		File file = new File(fileName);
		return file.exists();
	}

	/**
	 * Apaga um arquivo do disco.
	 * @param fileName
	 * @return
	 */
	public boolean deleteFile(String fileName) {
		File file = new File(fileName);
		return file.delete();
	}

	public static DataOutputStream abrirArquivo(String nomeArquivo, boolean isAdicionar) {
	    DataOutputStream stream = null;
	    
		try {
			stream = new DataOutputStream(new FileOutputStream(nomeArquivo, isAdicionar));
		} catch (FileNotFoundException ex) {
			logger.error(ex.toString());
		}
		return stream;
	}
	
	public static void adicionarLinhaArquivo(DataOutputStream stream, String texto) {
		try {
			stream.writeBytes(texto + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void fecharArquivo(OutputStream stream) {
		if (stream != null) {
			try {
				stream.write(26);
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Loads bytes from the given input stream until the end of stream is
	 * reached. It reads in at kDEFAULT_CHUNK_SIZE chunks.
	 * 
	 * @param stream
	 *            to read the bytes from
	 * @return bytes read from the stream
	 * @exception java.io.IOException
	 *                reports IO failures
	 */
	public static byte[] loadBytesFromStream(InputStream in) throws IOException {
		return loadBytesFromStream(in, DEFAULT_CHUNK_SIZE);
	}

	/**
	 * Loads bytes from the given input stream until the end of stream is
	 * reached. Bytes are read in at the supplied <code>chunkSize</code> rate.
	 * 
	 * @param stream
	 *            to read the bytes from
	 * @return bytes read from the stream
	 * @exception java.io.IOException
	 *                reports IO failures
	 */
	public static byte[] loadBytesFromStream(InputStream in, int chunkSize) throws IOException {
		if (chunkSize < 1)
			chunkSize = DEFAULT_CHUNK_SIZE;

		int count;
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		byte[] b = new byte[chunkSize];
		
		try {
			while ((count = in.read(b, 0, chunkSize)) > 0)
				bo.write(b, 0, count);
			byte[] thebytes = bo.toByteArray();
			return thebytes;
		} finally {
			bo.close();
			bo = null;
		}
	}
	
	/**
	 * Copia um arquivo para outro.
	 * @param src
	 * @param dst
	 * @throws IOException
	 */
	public static void copiarArquivo(String src, String dst) {
	    InputStream in = null;
	    OutputStream out = null;

	    try {
			in = new FileInputStream(src);

			File target = new File(dst);
			
		    if (target.getPath().indexOf('.') < 0) {
		    	target = new File(dst, (new File(src)).getName());
		    }
		    
			out = new FileOutputStream(target);

			// Transfer bytes from in to out
		    byte[] buf = new byte[1024];
		    int len;
		    
			while ((len = in.read(buf)) > 0) {
			    out.write(buf, 0, len);
			}

	    } catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
			
		} finally {
		    try {
				in.close();
				IOUtils.closeQuietly(in);
				in = null;
				
			    out.close();
			    out = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Faz uma chamada de comando do sistema operacional 
	 * @param command
	 * @return
	 */
	public static int shellExecCommand(String command) {
		int exitValue = 0;
		
		Runtime runtime = Runtime.getRuntime();
		Process process;
		
		try {
			process = runtime.exec(command);
		
			//Inicia o timeout
			new ExecProcessThread(1000, process);
			
			//Aguarda o processo terminar
			process.waitFor();

			//Verifica o valor de retorno
			exitValue = process.exitValue();
			
		} catch (IOException e) {
			e.printStackTrace();
			
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		return exitValue;
	}
	
	/**
	 * Copia um arquivo para outro usando chamada do sistema operacional
	 * @param source
	 * @param target
	 */
	public static void shellCopyFile(String source, String target) {
		String osName = System.getProperty("os.name").toLowerCase();
		String copyCmd = "cp";
		
		if (osName.indexOf("windows") > -1)
			copyCmd = "cmd /c copy";
		
		String command = String.format("%s %s %s", copyCmd, source, target);

		int exitValue = shellExecCommand(command);
		
		if (exitValue != 0) {
			logger.error("Erro ao copiar o arquivo. Codigo de retorno: " + exitValue);
		}
	}
	
	/**
	 * Apaga um arquivo
	 * @param nomeArquivo
	 * @return
	 */
	public static boolean deletarArquivo(String nomeArquivo) {
		File file = new File(nomeArquivo);
		
		boolean success = file.delete();
		
		if (!success) {
			logger.error("Não foi possível apagar o arquivo: " + nomeArquivo);
		}
		return success;
	}
	
	/**
	 * Move um arquivo (ou diretorio) para o destino especificado.
	 * @param origem
	 * @param destino
	 */
	public static boolean moverArquivo(String origem, String destino) {
		// File (or directory) to be moved
		File file = new File(origem);

		// Destination directory
		File dir = new File(destino);

		// Move file to new directory
		boolean success = false;
		
		logger.info("Move " + origem + " para " + dir.getAbsoluteFile());

		String testFile = origem;
		
		if (testFile.indexOf('.') > 0)
			testFile = testFile.substring(0, testFile.lastIndexOf('/'));
		
		if (testFile.equals(destino)) {
			file.renameTo(new File(dir, file.getName()));
		}
		else {
			copiarArquivo(origem, destino);
			
			file = null;
			success = (new File(origem)).delete();
		}
		
		if (!success) {
		    // File was not successfully moved
		}
		return success;
	}
	
	/**
     * Assegura que o diretorio existe. Se nao existe entao ele o cria.
     * @param nomeArquivo
     */
    public static void asseguraDiretorioExiste(String nomeArquivo) {
    	String diretorio = nomeArquivo;
    	
    	if (nomeArquivo.indexOf('.') > 0)
    		diretorio = nomeArquivo.substring(0, nomeArquivo.lastIndexOf('/'));
    	
		boolean success = (new File(diretorio)).mkdirs();
	    if (success) {
	      logger.info("Criado diretório: " + diretorio);
	    }		
    }
	
    
	/**
	 * Cria um arquivo baseado em um array de bytes
	 * @param nome
	 * @param dados
	 * @throws IOException
	 */
	public static void setFileFromBytes(String nome, byte[] dados) throws IOException {
		File file = new File(nome);
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(dados);
		fos.flush();
		fos.close();
	}	
	
	/**
	 * Cria um arquivo baseado em um array de bytes.
	 * Se o arquivo já existir, concatena um nome diferente ao final caso
	 * não deva sobrescrever.
	 * @param nome
	 * @param dados
	 * @param sobrescrever
	 * @throws IOException
	 */
	public static void setFileFromBytes(String nome, byte[] dados, boolean sobrescrever) throws IOException {
		File file = new File(nome);
		if(!sobrescrever){
			int i = 0;
			String tempName = nome;
			while(file.exists()){ // Arquivo existe
				File novoArquivo = new File(tempName);
				if(novoArquivo.exists()){
					tempName = tempName.substring(0,tempName.length() - 1) + i;
					i++;
					continue;
				}
				file.renameTo(novoArquivo);
				break;
			}
			file = new File(nome);
		}
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(dados);
		fos.flush();
		fos.close();
	}		
	
	/**
	 * Retorna array de bytes
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);
    
        // Get the size of the file
        long length = file.length();
    
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
    
        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];
    
        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
               && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }
    
        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }
    
        // Close the input stream and return bytes
        is.close();
        return bytes;
	} 

	/**
	 * Retorna o tamanho de um arquivo em disco.
	 * Se o arquivo nao existir retorna -1. 
	 * @param filename
	 * @return
	 */
	public static long getFileSize(String filename) {
		File file = new File(filename);

		if (!file.exists() || !file.isFile()) {
			return -1;
		}

		// Here we get the actual size
		return file.length();
	}
}

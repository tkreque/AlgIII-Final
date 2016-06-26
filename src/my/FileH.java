package my;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;


public class FileH<T> {
    private Scanner in = new Scanner(System.in);
    private Writer outCompressed,outDecompressed;
    private FileParser<T> objectParser;
    private Vetor<Character> vetor = new Vetor<>();
    private String filename, compressed, decompressed;
       
    public void open(String filename, String compressedFile, String decompressedFile){
        this.filename = filename;
        this.compressed = compressedFile;
        this.decompressed = decompressedFile;
        try{
            File file = new File(filename);
            in = new Scanner(file);
            outCompressed = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(compressedFile), "utf-8"));
            outDecompressed = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(decompressedFile), "utf-8"));
        }catch(Exception ex){
           	throw new RuntimeException(ex);
        }
    }	
    
    public void close(){
        try{
            in.close();
            outCompressed.close();
            outDecompressed.close();
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
            
    public Vetor<Node> readObject(){
        System.out.println("Iniciando a leitura do arquivo "+this.filename);
        String linha = "";
    	try{
            while(in.hasNextLine()){
                linha += in.nextLine(); 
            }
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    	char[] caracteres = linha.toCharArray();
    	Character[] arg = new Character[caracteres.length];
        
        for(int i=0; i<caracteres.length; i++){
    		arg[i] = caracteres[i];
    		vetor.insert(i, arg[i]);
    	}
    	
        System.out.println("Arquivo lido com sucesso! Criando o vetor");
        
        Vetor<Node> v = new Vetor<>();
        
        try{
            v = createStructure(vetor);
            System.out.println("Vetor criado com sucesso!");
        }catch(Exception ex){
            throw new RuntimeException("Falha ao criar o Vetor: "+ex);
        }
            
        return v;
        
    }
    
    public void writeObject(String s,int opcao){
        System.out.println("Salvando o arquivo...");
        try{
            if(opcao == 1){
                outCompressed.write(s);
                System.out.println("Arquivo salvo com sucesso!\n O caminho é: "+this.compressed);
            } else if (opcao == 2){
                outDecompressed.write(s);
                System.out.println("Arquivo salvo com sucesso!\n O caminho é: "+this.decompressed);
            } else
                throw new RuntimeException("Falha ao salvar o arquivo: Opção inválida");
            
        }catch(Exception ex){
            throw new RuntimeException("Falha ao salvar o arquivo: "+ex);
        }
        
    }
    
    public void setParser(FileParser parser){
       objectParser = parser;
    }
    
    public Vetor<Node> createStructure(Vetor<Character> v){
        Vetor<Node> v2 = new Vetor<>();
        for(int i=0; i<v.getSize(); i++){
            Node n = new Node(v.get(i).toString());

            if(v2.getSize()==0)
                v2.append(n);
            else
                for(int j=0; j<v2.getSize();j++)
                    if(n.getCaracteres()==v2.get(j).getCaracteres()){
                        v2.get(j).setFreq(v2.get(j).getFreq()+1);
                        break;
                    } else 
                        if(v2.getSize()==j+1){
                            v2.append(n);
                            break;
                        }
        }
        return v2;
    }	
}

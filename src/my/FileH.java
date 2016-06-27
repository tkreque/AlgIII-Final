package my;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Scanner;


public class FileH<T> {
    private Scanner in,inBin;
    private Writer outCompressed,outDecompressed;
    private FileParser<T> objectParser;
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
            throw new RuntimeException("Erro ao abrir o arquivo: "+ex);
        }
    }	
    
    public void close(int opcao){
        try{
            switch (opcao){
                case 1:
                    in.close();
                    break;
                case 2:
                    inBin.close();
                    break;
                case 3:
                    outCompressed.close();
                    break;
                case 4:
                    outDecompressed.close();
                    break;
                default:
                    throw new Exception("Opção inválida");
            }
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
            
    public Vetor<Node> readObject(){
        System.out.println("\nIniciando a leitura do arquivo "+this.filename);
        Vetor<Character> vetor = new Vetor<>();
        String linha = "";
    	
        try{
            while(in.hasNextLine())
                linha += in.nextLine(); 
            
            char[] caracteres = linha.toCharArray();
            Character[] arg = new Character[caracteres.length];

            for(int i=0; i<caracteres.length; i++){
                    arg[i] = caracteres[i];
                    vetor.insert(i, arg[i]);
            }
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    	
        System.out.println("Arquivo lido com sucesso! Criando o vetor...");
        
        Vetor<Node> v = new Vetor<>();
        
        try{
            v = createStructure(vetor);
            System.out.println("Vetor criado com sucesso!");
        }catch(Exception ex){
            throw new RuntimeException("Falha ao criar o Vetor: "+ex);
        }
            
        return v;
        
    }
    
    public String readBinary(){
        System.out.println("Iniciando a leitura do arquivo binário "+this.compressed);
        String bin = "";
        File fileBin = new File(this.compressed);
        
        try{   
            
            inBin = new Scanner(fileBin);
            while(inBin.hasNextLine())
                bin += inBin.nextLine(); 
            
        }catch(Exception ex){
            throw new RuntimeException("Erro na leitura binária: "+ex);
        }finally{
            close(2);
        }
        
        return bin;
    }
    
    public char[] readCaracters(){
        String linha = "";
        char[] caracteres;
        
        open(filename,compressed,decompressed);
        try{
            while(in.hasNextLine()){
                linha += in.nextLine(); 
            }
        
            caracteres = linha.toCharArray();
            
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
        
        return caracteres;
        
    }
    
    public void writeObject(String s,int opcao){
        System.out.println("Salvando o arquivo...");
        try{
            switch (opcao) {
                case 1:
                    outCompressed.write(s);
                    System.out.println("Arquivo salvo com sucesso!\nO caminho é: \t"+this.compressed);
                    close(3);
                    break;
                case 2:
                    outDecompressed.write(s);
                    System.out.println("Arquivo salvo com sucesso!\nO caminho é: \t"+this.decompressed);
                    close(4);
                    break;
                default:
                    throw new Exception("Opção inválida");
            }
            
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
                    if(n.getCaracteres().equals(v2.get(j).getCaracteres())){
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

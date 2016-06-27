package my;

class Node{
    private String caracteres,binario;
    private int freq;
    private Node esq, dir, leefDir, leefEsq;

    public Node(String ch){
        caracteres=ch;
        freq = 1;
        esq = null;
        dir = null;
        leefDir = null;
        leefEsq = null;
        binario = "";
    }
    
    public Node(String ch,int f){
        caracteres=ch;
        freq = f;
        esq = null;
        dir = null;
        leefDir = null;
        leefEsq = null;        
        binario = "";
    }

    public String getCaracteres(){
        return this.caracteres;
    }
    public int getFreq(){
        return this.freq;
    }
    public Node getEsq(){
        return this.esq;
    }
    public Node getDir(){
        return this.dir;
    }
    public Node getLeefDir(){
        return this.leefDir;
    }
    public Node getLeefEsq(){
        return this.leefEsq;
    }
    public String getBinario(){
        return this.binario;
    }
    
    public void setFreq(int i){
        this.freq = i;
    }
    public void setEsq(Node lft){
        this.esq = lft;
    }
    public void setDir(Node rgh){
        this.dir = rgh;
    }
    public void setLeefDir(Node ant){
        this.leefDir = ant;
    }
    public void setLeefEsq(Node prox){
        this.leefEsq = prox;
    }
    public void setBinario(String b){
        this.binario = b;
    }

    public int compareFreq(Node n){
        return this.freq - n.getFreq();
    }
    public boolean compareChar(Node n){
        if(this.caracteres == n.getCaracteres()){
            return true;
        }else{
            return false;
        }		
    }
}

public class Huffman {
    Node newRoot;
    Node node;
    String stringBinaria = "";
    String stringDecompressed = "";
    
    public void buildTree(Node root, Node ultimo){
        try{
            node = new Node(ultimo.getLeefEsq().getCaracteres() + ultimo.getCaracteres(), ultimo.getLeefEsq().compareFreq(ultimo));
            node.setEsq(ultimo.getLeefEsq());
            node.setDir(ultimo);
            ultimo.getLeefEsq().getLeefEsq().setLeefDir(node);
            node.setLeefEsq(ultimo.getLeefEsq().getLeefEsq());
            ultimo = node;
            ultimo.setLeefDir(null);
            Node atual = root;

            while (atual.getLeefDir() != null)
                atual = atual.getLeefDir();
            
            if (root.getLeefDir() == ultimo) {
                    node = new Node(root.getCaracteres() + ultimo.getCaracteres(), root.compareFreq(ultimo));
                    node.setEsq(root);
                    node.setDir(ultimo);
                    newRoot = node;
            } else {
                    buildTree(root, ultimo);
            }
        }catch(Exception ex){
            throw new RuntimeException("Erro na Tree: "+ex);
        }
    }
    
    public void compress(Vetor<Node> v, FileH ff){
        System.out.println("\nIniciando a compressão do arquivo...");
        try{
            Node root = null;
            Node atual = null;
            Node ultimo = null;

            for (int i = 0; i < v.getSize(); i++) {
                Node node = new Node(v.get(i).getCaracteres(), v.get(i).getFreq());
                if (root == null) {
                        root = node;
                        ultimo = node;
                } else {
                        atual = root;
                        while (atual.getLeefDir() != null) {
                                atual = atual.getLeefDir();
                        }
                        atual.setLeefDir(node);
                        atual.getLeefDir().setLeefEsq(atual);
                        ultimo = node;
                }
            }

            buildTree(root, ultimo);
            
            System.out.println("Construindo o binário do arquivo...");
            
            char[] arrayCaracteres = ff.readCaracters();
            char checker;

            for (int i = 0; i < arrayCaracteres.length; i++) {
                atual = node;
                checker = arrayCaracteres[i];
                String code = "";
                
                while (true) {
                    if (atual.getEsq().getCaracteres().toCharArray()[0] == checker) {
                        code += "0";
                        break;
                    } else {
                        code += "1";
                        if (atual.getDir() != null) {
                            if (atual.getDir().getCaracteres().equals(v.get(v.getSize()-1).getCaracteres())) {
                                break;
                            }
                            atual = atual.getDir();
                        } else {
                            break;
                        }
                    }
                    
                }
                
                for(int j=0;j<v.getSize();j++)
                    if(v.get(j).getCaracteres().toCharArray()[0] == checker)
                        v.get(j).setBinario(code);
                
                stringBinaria += code;
            }
            
            ff.writeObject(stringBinaria,1);
            
            
        }catch(Exception ex){
            throw new RuntimeException("Erro no compress: "+ex);
        }
    }
    
    public void decompress(Vetor<Node> v, FileH ff){
        System.out.println("\nIniciando a decompressão do arquivo...");
        String binary = "";
        try{
            
            binary = ff.readBinary();
            char[] arrayBinario = binary.toCharArray();
            
            String bin = "";
            
            for(int i=0;i<arrayBinario.length;i++){
                bin += arrayBinario[i];
                
                for(int j=0;j<v.getSize();j++)
                    if(v.get(j).getBinario().equals(bin)){
                        if(v.get(j).getCaracteres().equals("¬"))
                            stringDecompressed +="\n";
                        else
                            stringDecompressed += v.get(j).getCaracteres();
                        bin = "";
                    }
            }
            
            
            ff.writeObject(stringDecompressed,2);
                   
        }catch(Exception ex){
            throw new RuntimeException("Erro no decompress: "+ex);
        }
    }
    
}

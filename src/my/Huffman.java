package my;

class Node{
    private String caracteres;
    private int freq;
    private Node esq, dir, anterior, proximo;

    public Node(String ch){
        caracteres=ch;
        freq = 1;
        esq = null;
        dir = null;
        anterior = null;
        proximo = null;        
    }
    
    public Node(String ch,int f){
        caracteres=ch;
        freq = f;
        esq = null;
        dir = null;
        anterior = null;
        proximo = null;        
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
    public Node getAnterior(){
        return this.anterior;
    }
    public Node getProximo(){
        return this.proximo;
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
    public void setAnterior(Node ant){
        this.anterior = ant;
    }
    public void setProximo(Node prox){
        this.proximo = prox;
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
    
    public void buildTree(Node root, Node ultimo){
        try{
            node = new Node(ultimo.getProximo().getCaracteres() + ultimo.getCaracteres(), ultimo.getProximo().compareFreq(ultimo));
            node.setEsq(ultimo.getProximo());
            node.setDir(ultimo);
            ultimo.getProximo().getProximo().setAnterior(node);
            node.setProximo(ultimo.getProximo().getProximo());
            ultimo = node;
            ultimo.setAnterior(null);
            Node atual = root;

            while (atual.getAnterior() != null)
                atual = atual.getAnterior();
            
            if (root.getAnterior() == ultimo) {
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
        System.out.println("Iniciando a compressão do arquivo...");
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
                        while (atual.getAnterior() != null) {
                                atual = atual.getAnterior();
                        }
                        atual.setAnterior(node);
                        atual.getAnterior().setProximo(atual);
                        ultimo = node;
                }
            }

            buildTree(root, ultimo);
            
            System.out.println("Construindo o binário do arquivo...");
            for (int i = 0; i < v.getSize(); i++) {
                atual = node;
                String codigo = "";
                
                while (true) {
                    if (atual.getEsq().getCaracteres() == v.get(i).getCaracteres()) {
                        codigo += "0";
                        break;
                    } else {
                        codigo += "1";
                        if (atual.getDir() != null) {
                            if (atual.getDir().getCaracteres() == v.get(i).getCaracteres()) {
                                break;
                            }
                            atual = atual.getDir();
                        } else 
                            break;
                    }
                }
                stringBinaria += codigo;
            }
            
            ff.writeObject(stringBinaria,1);
            
            
        }catch(Exception ex){
            throw new RuntimeException("Erro no compress: "+ex);
        }
    }
    
    public void decompress(Vetor<Node> v, FileH ff){
        try{
            
        }catch(Exception ex){
            throw new RuntimeException("Erro no decompress: "+ex);
        }
    }
    
}

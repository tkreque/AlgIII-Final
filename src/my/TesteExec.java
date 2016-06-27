package my;

public class TesteExec {

    public static void main(String[] args) {
        String filename = "teste";
        String origin = "files/"+filename+".txt";
        String destination_compress = "files/"+filename+"_compress.txt";
        String destination_decompress = "files/"+filename+"_decompress.txt";
        Vetor<Node> v;
        FileH ff = new FileH();

        try{
            ff.open(origin,destination_compress,destination_decompress);
            v = ff.readObject();
            
            Huffman hf = new Huffman();
            
            hf.compress(v,ff);
            
            hf.decompress(v,ff);

        }catch(Exception ex){
            System.out.println("Erro: "+ex);
           
        }finally{
            ff.close(1);
        }

    }

}


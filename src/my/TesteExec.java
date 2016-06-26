package my;

public class TesteExec {

    public static void main(String[] args) {
        String filename = "teste4";
        String origin = "/home/reque/Codes/AlgIII Final/files/"+filename+".txt";
        String destination_compress = "/home/reque/Codes/AlgIII Final/files/"+filename+"_compress.txt";
        String destination_decompress = "/home/reque/Codes/AlgIII Final/files/"+filename+"_decompress.txt";
        Vetor<Node> v;
        FileH ff = new FileH();

        try{
            ff.open(origin,destination_compress,destination_decompress);
            v = ff.readObject();

            Huffman hf = new Huffman();
            
            hf.compress(v,ff);
            
            //hf.decompress(v,ff);

        }catch(Exception ex){
            System.out.println("Erro: "+ex);
           
        }finally{
            ff.close();
        }

    }

}


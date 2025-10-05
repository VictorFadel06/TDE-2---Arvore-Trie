public class ArvoreTrie {

    static class NoTrie {
        NoTrie[] filhos;   // vetor de filhos (1 para cada letra)
        boolean fimPalavra; // indica se é o fim de uma palavra

        NoTrie() {
            filhos = new NoTrie[26]; // 26 letras do alfabeto
            fimPalavra = false;
        }
    }

    NoTrie raiz;

    public ArvoreTrie() {
        raiz = new NoTrie();
    }


    public void inserir(String palavra) {
        NoTrie atual = raiz;

        int i = 0;
        while (i < palavra.length()) { // permitido usar length em String
            char c = palavra.charAt(i);
            int indice = c - 'a'; // converte caractere para índice (0 a 25)
            
            if (atual.filhos[indice] == null) {
                atual.filhos[indice] = new NoTrie();
            }

            atual = atual.filhos[indice];
            i++;
        }
        atual.fimPalavra = true;
    }

 
    public boolean buscar(String palavra) {
        NoTrie atual = raiz;
        int i = 0;

        while (i < palavra.length()) {
            char c = palavra.charAt(i);
            int indice = c - 'a';
            if (atual.filhos[indice] == null) {
                return false; // letra não encontrada
            }
            atual = atual.filhos[indice];
            i++;
        }

        return atual.fimPalavra;
    }

    
    public void remover(String palavra) {
        removerRec(raiz, palavra, 0);
    }

    private boolean removerRec(NoTrie no, String palavra, int pos) {
        if (no == null) return false;

        // caso base: fim da palavra
        if (pos == palavra.length()) {
            if (!no.fimPalavra) return false; // palavra não existe
            no.fimPalavra = false;
            // se não tem filhos, pode ser removido
            return semFilhos(no);
        }

        int indice = palavra.charAt(pos) - 'a';
        if (removerRec(no.filhos[indice], palavra, pos + 1)) {
            no.filhos[indice] = null;
            return !no.fimPalavra && semFilhos(no);
        }
        return false;
    }

    
    private boolean semFilhos(NoTrie no) {
        int i = 0;
        while (i < 26) {
            if (no.filhos[i] != null) return false;
            i++;
        }
        return true;
    }

    
    public void exibir() {
        char[] buffer = new char[50]; // limite de 50 letras
        exibirRec(raiz, buffer, 0);
    }

    private void exibirRec(NoTrie no, char[] buffer, int nivel) {
        if (no == null) return;

        if (no.fimPalavra) {
            // imprime palavra formada até aqui
            int i = 0;
            while (i < nivel) {
                System.out.print(buffer[i]);
                i++;
            }
            System.out.println();
        }

        int c = 0;
        while (c < 26) {
            if (no.filhos[c] != null) {
                buffer[nivel] = (char) ('a' + c);
                exibirRec(no.filhos[c], buffer, nivel + 1);
            }
            c++;
        }
    }

    // ----------------------------
    // Teste
    // ----------------------------
    public static void main(String[] args) {
        ArvoreTrie trie = new ArvoreTrie();

        trie.inserir("comida");
        trie.inserir("carvao");
        trie.inserir("casca");
        trie.inserir("cao");

        System.out.println("Palavras armazenadas na Trie:");
        trie.exibir();

        System.out.println("\nBuscar 'casca': " + trie.buscar("casca"));
        System.out.println("Buscar 'car': " + trie.buscar("car"));
        System.out.println("Buscar 'cao': " + trie.buscar("cao"));

        System.out.println("\nRemovendo 'casca'...");
        trie.remover("casca");

        System.out.println("\nPalavras após remoção:");
        trie.exibir();
    }
}

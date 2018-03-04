![alt tag](https://image.ibb.co/cxL9L7/oie_46110_KTed_V2_KJ.png)

# Desafio No.2 (Crawler) -- O Contexto
O Reddit é quase como um fórum com milhares de categorias diferentes. Com a sua conta, você pode navegar por assuntos técnicos, ver fotos de gatinhos, discutir questões de filosofia, aprender alguns life hacks e ficar por dentro das notícias do mundo todo!

Subreddits são como fóruns dentro do Reddit e as postagens são chamadas *threads*.

Para quem gosta de gatos, há o subreddit ["/r/cats"](https://www.reddit.com/r/cats) com threads contendo fotos de gatos fofinhos.
Para *threads* sobre o Brasil, vale a pena visitar ["/r/brazil"](https://www.reddit.com/r/brazil) ou ainda ["/r/worldnews"](https://www.reddit.com/r/worldnews/).
Um dos maiores subreddits é o "/r/AskReddit".

Cada *thread* possui uma pontuação que, simplificando, aumenta com "up votes" (tipo um like) e é reduzida com "down votes".

Sua missão é encontrar e listar as *threads* que estão bombando no Reddit naquele momento!
Consideramos como bombando *threads* com 5000 pontos ou mais.


## Regras do desafio

### Entrada
- Lista com nomes de subreddits separados por ponto-e-vírgula (`;`). Ex: "askreddit;worldnews;cats"

### Parte 1
Gerar e imprimir uma lista contendo número de upvotes, subreddit, título da thread, link para os comentários da thread, link da thread.
Essa parte pode ser um CLI simples, desde que a formatação da impressão fique legível.

### Parte 2
Construir um robô que nos envie essa lista via Telegram sempre que receber o comando `/NadaPraFazer [+ Lista de subrredits]` (ex.: `/NadaPraFazer programming;dogs;brazil`)


# Resolução

### "Building and running"
Antes de rodar a aplicação, CASO queira rodar os testes da aplicação em seu terminal, as dependências do projeto devem ser baixadas antes. Considerando que o Apache Maven já está instalado em seu ambiente, navegue até a pasta RAIZ do projeto usando um terminal de sua preferência e digite:
    
    $ mvn clean install

## 1- Aplicação CLI
Para rodar a aplicação CLI basta estar na pasta RAIZ do projeto e executar o jar `CliApplication.jar` passando os parâmetros envoltos por aspas `(")` e separados por ponto-e-vírgula (`;`), como no exemplo a seguir:

    $ java -jar CliApplication.jar "askreddit;worldnews;cats"
    
## 2- Aplicação Telegram-Bot
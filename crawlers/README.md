![alt tag](https://image.ibb.co/cxL9L7/oie_46110_KTed_V2_KJ.png)

# Desafio No.2 (Crawler) → O Contexto
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


# "Building and Runnig"


## 1- CrawlerCLI
Para rodar o projeto CrawlerCLI basta estar na pasta RAIZ do projeto e executar o comando a seguir (lembre-se de passar os parâmetros envoltos por aspas `(")` e separados por ponto-e-vírgula (`;`) ):

    $ java -jar target\CrawlerCLI-1.0.0-SNAPSHOT-jar-with-dependencies.jar "askreddit;worldnews;cats"

    
## 2- CrawlerTelegramBot
Para rodar a aplicação CrawlerTelegramBot você precisará ter um token. Para isso use o contato do [@BotFather](https://telegram.me/BotFather) no telegram para criar um novo bot e gerar o token necessário(usado para conectar-se à API do telegram). Caso o link não funcione, entre no telegram e procure pelo contato `@BotFather`.

![alt tag](http://image.ibb.co/ccFU57/find_botfather_ANDROID.jpg)

Após o encontrar clique no botão Start e escreva no chat:

    /newbot
    
Basta seguir as intruções que o `@BotFather` lhe dará para criar um bot com um nome e um username de sua preferência. Se tudo der certo, ao final você deverá receber uma mensagem como esta:

![alt tag](http://image.ibb.co/kqxdXn/botfather_token_message.png)
        
Copie o token gerado, vá até a pasta [src/main/resources](github.com/idwall/desafios/tree/master/crawlertelegrambot/src/main/resources) do projeto, abra o arquivo `config.properties` e cole o token como valor da chave `bot.token`(primeira linha não comentada) e salve o arquivo. Deverá ficar desta forma: `bot.token=SUA-API-KEY`

Agora, dentro de seu terminal, estando na pasta RAIZ do projeto execute o comando:

    $ mvn clean install

Após terminar o build da aplicação e suas baixar dependências, um `jar-with-dependencies` será gerado na pasta `target` do projeto. Execute o jar como no comando a seguir e deixe o terminal aberto:

    $ java -jar target\CrawlerTelegramBot-1.0.0-SNAPSHOT-jar-with-dependencies.jar
    
Se tudo der certo, a mensagem `System initialized successfully` deverá aparecer no seu terminal. Agora basta entrar no telegram e pesquisar pelo username do seu bot, assim como foi feito para o `@BotFather` (:

Após pressionar o botão `start` na conversa com o seu bot, experimente passar o comando `/NadaPraFazer askreddit;cats;dogs`

**Obs:** _Caso queira parametrizar alguma variável do sistema, como o **número mínimo de upvotes** para que uma thread seja considerada uma top thread, ou até mesmo a **quantidade de threads a serem exibidas na página** na hora da pesquisa, isso pode ser feito através do arquivo `properties.config`, bastando alterar os valores das chaves `param.minimum_upvotes` e `url.limit_param` (Não se esqueça de executar o comando `mvn clean install` novamente, para que seja gerado um novo `jar-with-dependencies` contendo as suas alterações.)_

# Built with

- [Java 1.8](https://docs.oracle.com/javase/8/docs/api/)
- [Apache Maven](http://maven.apache.org/guides/)
- [Junit 4.12](https://github.com/junit-team/junit4)
- [SeleniumHQ](https://github.com/SeleniumHQ)
- [HtmlUnitDriver](https://github.com/SeleniumHQ/htmlunit-driver)
- [Telegram Bots Api](https://core.telegram.org/bots/api)
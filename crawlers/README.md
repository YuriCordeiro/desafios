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


# Resolução

### "Building and running"
Antes de rodar a aplicação, CASO queira rodar os testes da aplicação em seu terminal, as dependências do projeto devem ser baixadas antes. Considerando que o Apache Maven já está instalado em seu ambiente, navegue até a pasta RAIZ do projeto usando um terminal de sua preferência e digite:
    
    $ mvn clean install

## 1- Aplicação CLI
Para rodar a aplicação CLI basta estar na pasta RAIZ do projeto e executar o jar `CliApplication.jar` passando os parâmetros envoltos por aspas `(")` e separados por ponto-e-vírgula (`;`), como no exemplo a seguir:

    $ java -jar CliApplication.jar "askreddit;worldnews;cats"
    
## 2- Aplicação Telegram-Bot
Para rodar a aplicação Telegram-bot, usaremos primeiro o contato do [@BotFather](https://telegram.me/BotFather) no telegram para criar um novo bot e gerar um `HTTP-API-TOKEN`(o qual será usado para conectar-se à API do telegram). Caso o link não funcione, entre no telegram e procure pelo contato `@BotFather`.

        IMAGEM VAI AQUI [FIND FOR @BOTFATHER]

Após encontrar clique no botão Start e escreva no chat:

    /newbot
    
Basta seguir as intruções que o `@BotFather` lhe dará para criar um bot com um nome e um username de sua preferência. Se tudo der certo, ao final você deverá receber uma mensagem como esta:

        IMAGEM 2 VAI AQUI [BOT FATHER MESSAGE CONTAINING THE API-TOKEN]
        
Copie o token gerado, vá até a pasta [src/main/resources]() do projeto, abra o arquivo `config.properties` e cole o token como valor da chave `bot.token`(primeira linha não comentada). Deverá ficar desta forma: `bot.token=SUA-API-KEY`

Pronto! Você já pode rodar a aplicação usando o comando `java -jar TelegramBotApplication.jar`. Se tudo der certo, a mensagem `System initialized successfully` deverá aparecer no seu terminal. Agora basta entrar no telegram e pesquisar pelo username do seu bot, assim como foi feito para o `@BotFather`

Após pressionar o botão `start` na conversa com o seu bot, experimente passar o comando `/NadaPraFazer askreddit;cats;dogs`

**Obs:** _Caso queira parametrizar alguma variável do sistema, como o **número mínimo de upvotes** para que uma thread seja considerada uma top thread, ou até mesmo a **quantidade de threads a serem exibidas na página** na hora da pesquisa, isso pode ser feito através do arquivo `properties.config`, bastando alterar os valores das chaves `param.minimum_upvotes` e `url.limit_param` (Pode ocasionar a alteração do tempo de resposta da aplicação)_
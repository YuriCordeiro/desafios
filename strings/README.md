﻿![alt tag](https://image.ibb.co/cxL9L7/oie_46110_KTed_V2_KJ.png)

# Desafio No.1 (Strings) → O Contexto
Após ler o coding style do kernel Linux, você descobre a mágica que é 
ter linhas de código com no máximo 80 caracteres cada uma.

Assim, você decide que de hoje em diante seus e-mails enviados também 
seguirão um padrão parecido e resolve desenvolver um plugin para te ajudar
com isso. Contudo, seu plugin aceitará no máximo 40 caracteres por linha.

Implemente uma função que receba: 
1. um texto qualquer
2. um limite de comprimento  

e seja capaz de gerar os outputs dos desafios abaixo.


## Regras do desafio
- O texto deve ser parametrizável e se quiser, pode utilizar um texto de input de sua preferência.

### Parte 1 (Básico) - limite 40 caracteres
Você deve seguir o exemplo de output [deste arquivo](https://github.com/idwall/desafios/blob/master/strings/output_parte1.txt), onde basta o texto possuir, no máximo, 40 caracteres por linha. As palavras não podem ser quebradas no meio.

### Parte 2 (Intermediário) - limite 40 caracteres
O exemplo de output está [neste arquivo](https://github.com/idwall/desafios/blob/master/strings/output-parte2.txt), onde além de o arquivo possuir, no máximo, 40 caracteres por linha, o texto deve estar **justificado**.


# "Building and Runnig"

Antes de rodar o projeto StringFormatter, todas as classes devem ser compiladas e as dependências baixadas via Maven. Execute o seguinte comando dentro do terminal na pasta RAIZ do projeto:

    $ mvn clean install
    
Após o término da instrução anterior, a pasta `target` contendo o `executable-jar` será gerada e a aplicação estará pronta para a execução. Ainda na pasta RAIZ do projeto, use o seguinte comando para executar o programa: `java -jar   target/StringFormatter-1.0-SNAPSHOT.jar [Uma sentença qualquer] [Máximo de caracteres por linha] [Justify flag]`, onde:
- [Uma sentença qualquer] = Uma sentença qualquer de sua preferência; _IMPORTANTE: Este parâmetro deve ser passado entre apóstrofes/aspas simples_.
- [Máximo de caracteres por linha] = Deverá ser um número;
- [Justify flag] = Deverá ser _true_ ou _false_;

Você pode rodar o seguinte exemplo (o diretório indicado no seu terminal deverá estar como dentro da pasta RAIZ do projeto StringFormatter para funcionar):

    $ java -jar target/StringFormatter-1.0-SNAPSHOT.jar 'In the beginning God created the heavens and the earth. Now the earth was formless and empty, darkness was over the surface of the deep, and the Spirit of God was hovering over the waters.`
    >
    > `And God said, "Let there be light," and there was light. God saw that the light was good, and he separated the light from the darkness. God called the light "day," and the darkness he called "night." And there was evening, and there was morning - the first day.' 30 false
    
_Se você resolver rodar a aplicação sem passar **parâmetros**, será executada usando a **sentença padrão**, usando o número **40** como **máximo de caracteres por linha** e **true** para a **justify flag**._
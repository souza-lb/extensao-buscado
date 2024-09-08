<h1 align=center>Projeto de Extensão BuscaDO</h1>

<b>Projeto de Extensão da Disciplina Java OO - Leonardo Bruno de Souza Silva - 202301011744 - Arquivos da Aplicação.</b>

<b>Repositório Pricipal do Projeto BuscaDO</b>

<b>Ambiente de desenvolvimento básico do projeto com a utilização do OpenJDK17, Maven, GeckoDriver e Docker (opcional)</b>

Este repositório fornece:

* pom.xml: Permite a construção do projeto utilizando o maven.

* /gecko: Fornece o GeckoDriver padrão utilizado no projeto ( Linux 64bits ).

* /src: Códigos de todas as classe Java do projeto.

* README.md: Estas instruções que você encontra abaixo.

Para criar o ambiente você vai precisar basicamente de:

* GNU Linux Debian 12.7.0
* OpenJDK17
* Apache Maven 3.8.7 ( ou superior )
* Gecko Driver Linux amd64 ( já incluído no repositório )
* Git 2.39.2 ( ou superior) opcional
* Docker 20.10.24 ( ou superior - opcional ) opcional

Sistema Operacional Utilizado:  GNU/Linux Debian 12.7.0  


<h2>Como rodar o projeto localmente ?</h2>

Para instalação dos pacotes básicos 
execute no terminal:

```bash
$ sudo apt install openjdk-17-jdk maven git docker.io docker-compose
```

Com o git instalado ou pelo navegador clone esses dois repositórios abaixo:

extensao-buscado ( repositório principal do projeto )
```bash
git clone https://github.com/souza-lb/extensao-buscado.git
```
extensao-buscado-docker ( repositório com arquivos docker para construção do ambiente e implantação )
```bash
git clone https://github.com/souza-lb/extensao-buscado-docker.git
```


Dentro da pasta do primeiro repositório que você encontra:  

![Pasta repositório extensão-buscado-main](/imagens/pasta-extensao-buscado-main.png)  

Acese a pasta "/src/main/resorces/" e edite o arquivo .env no seu interior.  
Neste arquivo você define o nome para busca, dados de email e seu bot Telegram  

O arquivo segue esse padrão:

```
NOME_BUSCA=Nome Exemplo
TOKEN_TELEGRAM=23027461345:ABGJhxjekNG9mzldklkddjkdhar8jfazRGga4
CHAT_ID_TELEGRAM=3529167404
EMAIL_ENVIO=exemplo@gmail.com
SENHA_EMAIL=evuk iwnk kgdf zywn
EMAIL_DESTINO=exemplo@exemplo.com
```
Insira seus dados salve o arquivo e retorna a pasta raiz. Vamos iniciar o processo de construção do arquivo "jar" com as dependências.

Execute agora :

```bash
sudo mvn clean
```
Se tudo der certo você receberá esta saída:  

![Saída comando mvn clean](/imagens/mvn-clean.png)  

Execute agora :

```bash
sudo mvn package
```

Se tudo ocorrer como esperado você receberá esta saída:  

![Saída comando mvn package](/imagens/mvn-package.png)  

Agora acesse a nova pasta "target", nela você encontrar os arquivos "jar" do projeto:  

![Pasta target](/imagens/pasta-target.png)  

Copie o arquivo "extensao-buscado-1.0.0-jar-with-dependencies.jar" para uma pasta de sua preferência.  
Copie a pasta "/gecko" para o mesmo local que você copiou o arquivo "jar"

Dê permissão de execução para o arquivo "geckodriver" dentro da pasta "/gecko".  

```bash
chmod +x geckodriver
```

Para iniciar o programa execute no terminal:  

```bash
java -jar extensao-buscado-1.0.0-jar-with-dependencies.jar
```

Se o software iniciar dentro de alguns instantes você receberá uma janela de notificação conforme abaixo:  

![Janela alerta](/imagens/janela-alerta-app.png)  

Se você clicar no botão "Abrir PDF" você receberá uma janela conforme abaixo:  

![Janela arquivo PDF](/imagens/janela-arquivo-pdf-do.png)  

O software utilizará o seu visualizador de arquivos "PDF" padrão.

Em alguns instantes você receberá sua notificação por Telegram conforme abaixo:  

![Notificação Telegram](/imagens/notificacao-telegram.png)


Em seguinda você receberá sua notificação por E-Mail conforme abaixo:  

![Notificação E-Mail](/imagens/notificacao-email.png)  



<h2>Como rodar o projeto utilizando o Docker ?</h2>

Realize as etapas anteriores incluindo o passo para gerar o arquivo "jar"

Agora acesse a pasta do segundo repositório que você clonou (extensao-buscado-docker)

Copie o seu "fat jar" para a pasta conforme abaixo:  

![Pasta repositório extensão-buscado-docker-main](/imagens/pasta-extensao-buscado-docker-main.png)  

Abra um terminal na pasta raiz do repositorio que você clonou e adicionou o arquivo "jar".

Para criar a imagem docker execute neste terminal:  

```bash
$ sudo docker-compose build
```

Você terá uma saída como abaixo:  

![Saída comando docker-compose build](/imagens/docker-compose-build.png)  

Isso vai damorar um pouco na primera vez pois vai baixar a imagem para o repositório local.

Agora execute no terminal:

```bash
$ sudo docker-compose up
```
Você também pode rodar o comando acima com o parâmetro -d como resultado seu terminal fica livre

```bash
$ sudo docker-compose up -d
```

Você receberá uma saída conforme abaixo:  

![Saída comando docker-compose up](/imagens/docker-compose-up.png)  

Neste ponto seu sóftware já está rodando. Em alguns segundo a janela de notificação abrirá.

<h2>Como rodar o projeto usando o IDE Eclipse ?</h2>

Utilize a opção para importar um projeto existente maven conforme abaixo:  

![Janela inicial inportação Maven Eclipse](/imagens/eclipse-projeto-maven-existente.png)  

Avançe para a próxima tela conforme abaixo:  

![Janela Maven Eclipse POM](/imagens/eclipse-projeto-maven-existente-pom.png)  

Rode a classe principal "Main.java"  

Esta classe concentra as principais funções da aplicação. Nela você define os horários de agendamento e serviços de notificação que você deseja utilizar.  

![Eclipse classe Main](/imagens/classe-main-eclipse.png)  

Seu programa já está funcionando:  

![Janela Eclipse rodando](/imagens/classe-main-eclipse-rodando.png)  


Este repositório foi criado por: <b>Leonardo Bruno de Souza Silva</b><br>
<b>Matrícula 202301011744</b><br>
<b>Projeto de Extensão BuscaDO da Disciplina Java Orientado à Objeto</b><br>
202301011744@alunos.estacio.br<br>
<b>souzalb@proton.me</b>


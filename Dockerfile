#============================================================================================
# FROM [nome da imagem]:[versão/tag da imagem]
# Referência: https://docs.docker.com/engine/reference/builder/#from
# 
# Define uma imagem local ou pública do Docker Store.
#============================================================================================
FROM adoptopenjdk:11-jre-hotspot

#============================================================================================
# LABEL maintainer=[nome e e-mail do mantenedor da imagem]
# Referência: https://docs.docker.com/engine/reference/builder/#label
#
# Indica o responsável/autor por manter a imagem.
#============================================================================================
LABEL maintainer="Gabriel Potje <gpotje.souza@gmail.com.br>"

#============================================================================================
# ARG <nome do argumento>[=<valor padrão>]
# Referência: https://docs.docker.com/engine/reference/builder/#arg
#
# A instrução ARG define uma variável que os usuários podem passar no tempo de compilação 
# para o construtor com o comando docker build.
#============================================================================================
ARG JAR_FILE

#============================================================================================
# ENV [nome da variável de ambiente]
# Referência: https://docs.docker.com/engine/reference/builder/#env
# 
# Variáveis de ambiente com o path da aplicação dentro do container.
#============================================================================================
ENV \
  DATABASE_URL='jdbc:postgresql://127.0.0.1:5432/postgres' \
  DATABASE_USERNAME='postgres' \
  DATABASE_PASSWORD='debian23' 

#============================================================================================
# COPY [arquivo a ser copiado] [destino do arquivo copiado]
# Referência: https://docs.docker.com/engine/reference/builder/#copy
#
# Copia o arquivo da aplicação, cuja nomenclatura é obtida a partir da variável ${JAR_FILE},
# para dentro do container sob o nome app.jar.
#============================================================================================
COPY ${JAR_FILE} /app.jar

#============================================================================================
# EXPOSE [número da porta]
# Referência: https://docs.docker.com/engine/reference/builder/#expose
#
# Irá expor a porta para a máquina host (hospedeira). É possível expor múltiplas portas, como 
# por exemplo: EXPOSE 80 443 8080
#============================================================================================
EXPOSE 3030


#============================================================================================
# ENTRYPOINT [executável seguido dos parâmetros]
# Referência: https://docs.docker.com/engine/reference/builder/#entrypoint
# 
# Inicia o container como um executável a partir da inicialização da aplicação. Essa instrução 
# é muito útil quando o container está em modo Swarm (Cluster de containers), pois caso a 
# aplicação caia, o container cai junto, indicando ao cluster aplicar a política de restart 
# configurada para a aplicação.
#============================================================================================
#============================================================================================
# HEALTHCHECK --interval=[duração em segundos] --timeout=[duração em segundos]
# Referência: https://docs.docker.com/engine/reference/builder/#healthcheck
# 
# Diz ao Docker como testar um container para verificar se ele ainda está funcionando. Isso 
# pode detectar casos como um servidor web que está preso em um loop infinito e incapaz de 
# lidar com novas conexões, mesmo que o processo do servidor ainda esteja em execução.
#============================================================================================




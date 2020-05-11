# Webservice de consulta de estados e municípios do Brasil
O projeto consiste em experimento de implementação de um webservice reativo para consulta de estados e municípios construída com Spring Webflux. Na aplicação, é utilizado o serviço público do IBGE para consulta, através da URL "https://servicodados.ibge.gov.br/api/v1/localidades/estados". Para fazer essa consulta, foi utilizado o Spring WebClient, bilioteca para consumir serviços de forma "reativa".

São fornecidos três rodas nesa aplicação:
1. localhost:8081/api/localidades/municipios: retorna todos os municípios do Brasil em formato JSON;
1. localhost:8081/api/localidades/municipios.csv: retorna todos os municípios do Brasil em um arquivo ".csv" (download);
1. localhost:8081/api/localidades/municipio?nomeCidade=nomeDaCidade: faz uma busca da cidade "nomeDaCidade" e retorna apenas o seu id;

## Como excecutar essa aplicação

[em breve]

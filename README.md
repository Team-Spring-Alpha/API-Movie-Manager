<h1 align="center"> Apollo70 Filmes - API-Movie-Manager</h1>

![Badge](http://img.shields.io/static/v1?label=STATUS&message=DEVELOPMENT&color=yellow&style=for-the-badge)
![Badge](http://img.shields.io/static/v1?label=RELEASE%20DATE&message=SEPTEMBER%202022&color=yellow&style=for-the-badge)

<p align="center">O projeto Apollo70 Filmes consiste em uma aplicação de uma locadora de filmes. A aplicação é dividida em microserviços que detém as funcionalidades necessárias para o usuário ter acesso a um catálogo de filmes disponíveis para compra e alocação.<p>

## Fluxo de Funcionamento da Aplicação

`Movie-Manager`tem conexão com a API externa [The Movie DB](https://www.themoviedb.org/documentation/api) que disponibiliza acesso a dados de filmes, atores e programas de TV.

## Funcionalidades

| Método | Caminho | Descrição |
|---|---|---|
| `GET` | /api/movie-manager/movie-search/?{filtros}| Busca por filmes com os filtros de nome, gênero, stream, data de lançamento e atores |
| `GET` | /api/movie-manager/{id}/recommendations | Busca filmes recomendados conforme o id do filme passado como parâmetro |

## Tecnologias utilizadas
- Spring Validation
- Spring Web
- Spring OpenFeign
- Spring Fox
- ModelMapper
- Lombok
- Swagger

> Para saber mais acesse a [Documentação](https://moviemanagerapi.herokuapp.com/swagger-ui/index.html)

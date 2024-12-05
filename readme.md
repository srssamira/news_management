## Sistema Simplificado de Verificação de Notícias

### 1. Organização do Projeto

O projeto é estruturado utilizando a Arquitetura Hexagonal (Ports and Adapters), que separa a lógica de negócio do código que interage com infraestruturas externas. Abaixo está a organização geral do projeto:

1.1. Pacotes Principais

- `/domain:` Contém a lógica de negócio e as entidades principais.
- `/domain/models:` NewsItem
- `/domain/dtos:` NewsVerificationRequestDTO, NewsVerificationResponseDTO, NewsRegisterDTO
- `/domain/usecases:` NewsVerificationUseCase, NewsRegisterUseCase

- `/app:` Camada de aplicação, intercede os domínios de os adaptadores.
- `/app/ports:` Entrada (NewsController)
- `/app/infra:` Interface que define ponto de interação entre o domain e os adapters
- `/app/adapters:` Saída (NewsItemRepository), implementações (NewsAdapters, NewsVerificationAdapters).


### 2. Estrutura do Projeto

2.1. Domínio

2.1.1. Entidades
- NewsItem: Representa uma notícia.
```
Atributos:

- Long id: ID notícia.
- String url: URL da notícia.
- String text: Texto completo da notícia.
- LocalDate publicationDate: Data de publicação da notícia.
```
```
Métodos:
Getters e setters para os atributos.
```


- NewsVerificationRequestDTO: Representa os atributos booleanos utilizados para a verificação.
```
Atributos:

boolean haveCommunicationVehicle
boolean haveAuthor
boolean havePublicationDate
boolean haveTrustedSource
boolean haveSensacionalistLanguage
```
```
Métodos:
Getters e setters para os atributos.
```

- NewsVerificationResponseDTO:
  Representa o resultado da verificação.
```
Atributos:

Long id: ID da notícia.
String classification: Classificação da notícia (high confidence, medium confidence, low confidence, high suspicion.).
int score: Pontuação calculada.
String url: URL da notícia verificada.
```
```
Métodos:
Getters e setters para os atributos.
```

2.1.2. UseCases

- NewsVerificationUseCase:
  Implementa a lógica de classificação de notícias.
```
Métodos:
- NewsVerificationResponseDTO verifyNews(NewsVerificationRequestDTO verificationRequest, Long id)
```
**Lógica:**
1. Inicializa a pontuação em 0.
2. Para cada atributo, aplica os pesos definidos na matriz de regras:
```
haveCommunicationVehicle: +3 se verdadeiro, 0 se falso.
haveAuthor: +2 se verdadeiro, 0 se falso.
havePublicationDate: +1 se verdadeiro, 0 se falso.
haveTrustedSource: +5 se verdadeiro, -3 se falso.
haveSensacionalistLanguage: -3 se verdadeiro, 0 se falso.
```
3. Calcula a classificação com base na pontuação:
```
score >= 8: HIGH CONFIDENCE.
3 <= score < 8: MEDIUM CONFIDENCE.
-2 <= score < 3: LOW CONFIDENCE.
score < -2: HIGH SUSPICION.
Retorna um objeto NewsVerificationResponseDTO com a classificação, o score e a URL.
```

2.2. Pacote app

2.2.1. /port

**Endpoints:**

POST
`/api/news`: Registra uma notícia no banco de dados.
```
json:
{
  "url": "https://www.exemplo.com/noticia",
  "texto": "Texto completo da notícia.",
  "dataPublicacao": "2023-10-01"
}
response - 201:
{
  "mensagem": "News registered successfully",
  "id": 1
}

response - 400:
{
  "error": "Invalid input data",
  "description": "The 'url' field is mandatory."
}
```

POST `/api/news/verify`:
Verifica a confiabilidade de uma notícia com base nos atributos booleanos.
```
json:
{
  "Long id": "1",
  "haveCommunicationVehicle": true,
  "haveAuthor": true,
  "havePublicationDate": true,
  "haveTrustedSource": false,
  "haveSensacionalistLanguage": true
}

response - 200
{
  "classification": "LOW CONFIDENCE",
  "score": 1,
  "url": "https://www.example.com/news"
}

response - 404:
{
  "error": "News not found"
}
```
2.2.2. /infra

- NewsItemRepository:
  Interface para persistência de objetos NewsItem.
```
Métodos:

Optional<NewsItem> findById(Long id): Busca uma notícia pelo id.

NewsItem save(NewsItem newsItem): Salva uma notícia no banco de dados.
```

2.2.3. /adapters
NewsVerificationAdapter:
Implementa a lógica de classificação com base na matriz de regras.
```
Métodos:
NewsVerificationResponseDTO verificarNoticia(NewsVerificationRequestDTO requestDTO, Long id)
```


### 3. Fluxo de Requisição
- O cliente faz uma requisição POST para o endpoint /api/news para registrar uma notícia.
- O cliente faz uma requisição POST para o endpoint /api/news/verify para verificar a confiabilidade da notícia.
- O NewsController valida os dados e invoca os serviços correspondentes.




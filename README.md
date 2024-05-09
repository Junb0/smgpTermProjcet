# spgpTermProjcet
## Meme Cat Defense
한국공학대학교 24학년도 1학기 스마트폰게임 프로그래밍 텀프로젝트
2019182017 박준영

1차 발표
---
<https://youtu.be/BQJ8xp1PHGI>

2차 발표
---
<https://youtu.be/Xqll0xLy4E8>

### 게임 컨셉
#### High Concept
랜덤으로 뽑은 고양이를 조합하여 적의 물량 공세를 막아내라!
#### 핵심 메카닉
- 골드를 소모하여 랜덤한 고양이를 소환
- 같은 종류의 고양이를 합쳐 높은 등급의 고양이 생성
- 배치된 고양이들이 레일을 따라 이동하는 적을 공격
- 적이 목적지에 도달하기 전에 처치
- 최대한 적을 막아내며 높은 웨이브에 도달

#### 개발 의도
![콘셉](https://github.com/Junb0/smgpTermProjcet/blob/main/docs/concept.png)
'랜덤 다이스'와 같은 랜덤 디펜스게임 + 컬트적 인기를 얻고있는 밈 고양이들을 출현시켜 유머러스함 강조
#

### 개발 범위
![개발범위](https://github.com/Junb0/smgpTermProjcet/blob/main/docs/%EA%B0%9C%EB%B0%9C%20%EB%B2%94%EC%9C%84.png)

#
### 고양이 종류
#### 해피캣
- 메인 컬러 : 보라색
- 공격력 : 100
- 공격속도 : 1.5 (1초에 1.5회 공격)
- 별이 늘어날수록 공격속도 10% 증가
#### 애플캣
- 메인 컬러 : 파란색
- 공격력 : 100
- 공격속도 : 1
- 공격시 지름 0.25의 원 범위에 범위 공격
- 별이 늘어날 수록 공격 범위 10% 증가
#### 바나나캣
- 메인 컬러 : 초록색
- 공격력 : 100
- 공격속도 : 1
- 웨이브 시작 시 1초당 2% 씩 공격력 상승, 다음 웨이브에서 이 수치는 초기화 최대 50% 증가
- 별이 늘어날 수록 초당 공격력 상승 1%, 최대 상승 수치 30% 증가
#### 맥스웰캣
- 메인 컬러 : 하늘색
- 공격력 : 100
- 공격속도 : 1
- 자신의 상하좌우 칸에 있는 고양이의 공격력 20% 상승
- 별이 늘어날 수록 공격력 버프량 10% 증가
#### oii캣
- 메인 컬러 : 빨간색
- 공격력 : 80
- 공격속도 : 1
- 적 공격 시 5%의 확률로 적의 이동을 3초간 정지시킨다.
- 별이 늘어날 수록 확률 2%, 잠금시간 0.5초 증가 

#

### 적 종류
#### 네모
- 기본적인 형태의 적
#### 육각형
- 파괴시 네모가 생성
#### 원
- 파괴시 육각형 생성

### 예상 게임 흐름
#### 예상 게임 화면
![게임화면](https://github.com/Junb0/smgpTermProjcet/blob/main/docs/%EC%98%88%EC%83%81%20%EA%B2%8C%EC%9E%84%20%ED%99%94%EB%A9%B4.png)
#### 고양이 소환
![고양이소환](https://github.com/Junb0/smgpTermProjcet/blob/main/docs/%EA%B3%A0%EC%96%91%EC%9D%B4%20%EC%86%8C%ED%99%98.png)
#### 적 처치
![적처치](https://github.com/Junb0/smgpTermProjcet/blob/main/docs/%EC%A0%81%20%EC%B2%98%EC%B9%98.png)
#### 고양이 합성
![합성](https://github.com/Junb0/smgpTermProjcet/blob/main/docs/%EA%B3%A0%EC%96%91%EC%9D%B4%20%ED%95%A9%EC%84%B1.png)
#### 고양이 업그레이드
![업글](https://github.com/Junb0/smgpTermProjcet/blob/main/docs/%EA%B3%A0%EC%96%91%EC%9D%B4%20%EC%97%85%EA%B7%B8%EB%A0%88%EC%9D%B4%EB%93%9C.png)
#### 흐름도
![흐름도](https://github.com/Junb0/smgpTermProjcet/blob/main/docs/%EA%B2%8C%EC%9E%84%20%ED%9D%90%EB%A6%84%EB%8F%84.png)

#

### 개발 일정 및 진행 상황
   | 주차  | 구현 내용                                             | 진행율                                                    |
   | ----- | ----------------------------------------------------- | ------------------------------------------------------------ |
   | 1주차 | 프로젝트 세팅, 리소스 수집 및 제작                       | 100%                                                        |
   | 2주차 | 리소스 수집 및 제작, 게임화면 레이아웃 제작              | 80%                                                         |
   | 3주차 | 고양이 오브젝트 소환 및 배치 제작                        | 100%                                                       |
   | 4주차 | 고양이 합성, 업그레이드 시스템 제작                      | 100%                                                       |
   | 5주차 | 적 객체 소환 및 이동 구현                                | 100%                                                       |
   | 6주차 | 공격 및 웨이브 시스템 구현                              | 20%                                 |
   | 7주차 | UI 제작, 사운드 추가                                    |         |
   | 8주차 | 디버깅 및 밸런스 수정, 추가 구현                        |                       |
   | 9주차 | 디버깅 및 밸런스 수정, 발표 자료 제작                   |                                        |

#### 핵심 시스템 구현 예시 (소환/합성)
![소환합성](https://github.com/Junb0/smgpTermProjcet/blob/main/docs/spawnAndMerge.gif)

#### 주차별 커밋 내역
![커밋횟수](https://github.com/Junb0/smgpTermProjcet/blob/main/docs/%EC%BB%A4%EB%B0%8B%ED%9A%9F%EC%88%98.png)
| 주차  | 횟수 |
| :---: | :--: |
| 1주차 |  3   |
| 2주차 |  1   |
| 3주차 |  0   |
| 4주차 |  5   |
| 5주차 |  5   |
| 6주차 |  15   |

#

### GameObject

- Cat

  ![고양이예제](https://github.com/Junb0/smgpTermProjcet/blob/main/docs/CatSample.png)
  - 주요 속성
    - 고양이 종류, 별 개수, 자신이 위치한 슬롯의 인덱스
  - 동작 구성
    - 일정시간마다 선두의 적에게 투사체를 발사한다. (구현중)
  - 상호작용
    - 터치 -> 자신의 속성을 물려받는 DraggedCat 오브젝트를 생성 한다.
    - DraggedCat -> 합성이 성공할 경우 등급이 오르며 랜덤으로 타입을 변경 한다.
    - 공격 수행 -> UpgradeManager에 접근하여 최종 데미지 산출
  
#

- DraggedCat

  ![드래그고양이](https://github.com/Junb0/smgpTermProjcet/blob/main/docs/draggedCat.png)
  - 주요 속성
    - Cat과 동일
  - 동작 구성
    - 플레이어가 터치중인 지점을 따라간다.
  - 상호작용
    - 터치 종료 -> 해당 slot에 존재하는 Cat 객체와 합성 가능 여부 검사, 합성 수행

#
  
- CatSpawner
  - 주요 속성
    - 소환 가격, 비어있는 슬롯 정보
  - 동작 구성
    - 소환 버튼 터치 시 골드를 소모하고, 비어 있는 임의의 슬롯에 고양이 객체를 생성한다.

#

- Enemy

  ![적](https://github.com/Junb0/smgpTermProjcet/blob/main/docs/enemy.png)
  - 주요 속성
    - HP, 이동속도, 적 종류
  - 동작 구성
    - 화면 중앙의 레일을 따라 EndPoint까지 이동한다.
  - 상호 작용
    - EndPoint 도달 -> 플레이어의 HP 10 감소, 객체 삭제
    - 고양이의 투사체 충돌 -> HP가 감소하며 HP가 0 이하가 되면 삭제 -> 플레이어 골드 증가

#

- EnemySpawner
  - 주요 속성
    - 적 소환 쿨타임
  - 동작 구성
    - 일정 시간마다 StartPoint에 적 객체를 생성한다.

#
 
- UpgradeManager
  - 주요 속성
    - 고양이 타입에 따른 업그레이드 현황 배열
  - 상호 작용
    - 업그레이드 버튼 터치 -> 해당 고양이 타입의 업그레이드 카운트를 증가시킨다.
  
  

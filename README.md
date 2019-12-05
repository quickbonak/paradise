포트폴리오 업로드용 깃 개편중

각 폴더는 홈페이지 각 프로젝트들의 소스코드들입니다.
(기존 jsp MVC1 프로젝트를 MVC2 스프링 프로젝트로 고쳐본 것은 아직 정리중이라 홈페이지의 설명에는 없습니다. )


아래는 꿀벌 철인 게임의 최종 경기에서 다른 플레이어의 고유 번호를 랜덤하게 뽑아오는 알고리즘 입니다.

db에서 선수 수를 가져온 다음, 중복없는 무작위 선별 후, 선수 idx에 이빨이 빠진 경우에도

무사히 출전선수 9명을 불러올 수 있도록 디자인 하였습니다.

아직 설명이 부족하지만, 이러한 알고리즘으로 차후 중복없이 랜덤을 생성하는 라이브러리를 만들어 배포 예정입니다.

중복되는 수가 생기면 중복이 없을 때 까지 랜덤함수를 호출하는 방식이 아닌, 애초에 중복되지 않는 난수를 생성하는 알고리즘 입니다.

랜덤함수의 잦은 중복 때문에 연산량이 늘어나는 경우에 사용하면 프로세스 효율성이 높아질 것입니다.
(예를들어 1~10 까지의 난수를 9개의 변수에 중복없이 할당하는 경우가 있겠습니다.)

현 완성물 중에 제 공학적 사고의 한계를 잘 보여줄 수 있는 예제라 생각되어 이렇게 리드미에 게시합니다.



int[] playerIdx = new int[9];
int[] tempThPlayer = new int[9];
int tempNum;

sql = "select idx from finalresult order by idx asc";
pstmt = con.prepareStatement(sql);
rs = pstmt.executeQuery();

for (int i = 0; i < 9; i++) {
    tempNum = random.nextInt(numOfPlayers-i-1)+1;
    int dupleCount = 0;
    int lastCount = 0;
    boolean goLoop = true;
    
    while(goLoop) {
        for (int j = 0; j < i; j++) {
            if(tempNum+lastCount>=tempThPlayer[j]) {
                dupleCount++;
            }
        }
        if(lastCount==dupleCount)goLoop=false;
        lastCount=dupleCount;
        dupleCount=0;
    }
    
    tempThPlayer[i]=tempNum+lastCount;
    rs.absolute(tempThPlayer[i]);
    playerIdx[i]=Integer.parseInt(rs.getString("idx").toString());
}
rBean.setPlayeridx(playerIdx);

pstmt.close();
rs.close();
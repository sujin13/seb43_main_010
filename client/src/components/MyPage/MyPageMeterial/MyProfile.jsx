import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import BTS from '../../../assets/jpg-file/card-jpg/1-bts.jpg';
import TXT from '../../../assets/jpg-file/card-jpg/2-txt.jpg';
import NewJeans from '../../../assets/jpg-file/card-jpg/3-newJeans.jpg';

const LeftWrapper = styled.div`
  display: flex;
  flex-direction: column;
`;

const LeftBox = styled.div`
  width: 334px;
  height: 208px;
  margin-top: 166px;
  border-radius: 16px;
  background-color: var(--white-100);
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const Img = styled.div`
  width: 124px;
  height: 64px;
  border-radius: 5px;

  img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    border-radius: 5px;
  }
`;

const Name = styled.span`
  font-size: 35px;
  font-weight: 900;
  color: var(--gray-980);
  margin-bottom: 11px;
  display: block;
`;

const NickName = styled.span`
  font-size: 17.5px;
  font-weight: 700;
  color: var(--gray-980);
  margin-bottom: 3px;
  display: block;
`;

const ArtistName = styled.span`
  font-size: 12.5px;
  font-weight: 500;
  color: var(--light-gray-500);
  margin-bottom: 20px;
  display: block;
`;

const MembershipDate = styled.span`
  font-size: 12.5px;
  font-weight: 500;
  color: var(--light-gray-300);
  display: block;
`;

const Email = styled.span`
  font-size: 23px;
  font-weight: 500;
  color: var(--dark-blue-700);
  margin-bottom: 20px;
  display: block;
`;

const LogoutBtn = styled.button`
  height: 16px;
  color: var(--gray-blue-400);
  font-size: 13px;
  font-weight: 500;
  display: block;
  cursor: pointer;
  text-decoration: underline;
`;

const Container = styled.div`
  display: flex;
  flex-direction: column;
  width: 334px;
  height: 669px;
  margin-top: 23px;
`;

const Title = styled.span`
  font-size: 23px;
  font-weight: bold;
  color: var(--gray-980);
  margin-bottom: 20px;
`;

const Box = styled.div`
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 334px;
  height: 215px;
  margin-bottom: 20px;
  border-radius: 16px;
  background-color: var(--white-100);
`;

const DeleteBtn = styled.button`
  width: 60px;
  position: absolute;
  top: 0;
  right: 0;
  height: 16px;
  color: var(--gray-blue-300);
  font-size: 13px;
  font-weight: 500;
  cursor: pointer;
  text-decoration: underline;
`;

const ModalWrapper = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
`;

const ModalContent = styled.div`
  background-color: var(--white-100);
  width: 428px;
  height: 302px;
  border-radius: 14px;
  padding: 70px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const ModalTitle = styled.div`
  font-size: 15px;
  font-weight: 600;
  margin-top: 20px;
  margin-bottom: 20px;
`;

const ModalText = styled.div`
  display: flex;
  /* text-align: left; */
  /* align-items: flex-start; // text와 ✰의 정렬을 위해 추가 */
  font-size: 13px;
  font-weight: 500;
  line-height: 1.5;
`;

const ModalTextBold = styled.div`
  font-size: 12.8px;
  font-weight: 600;
  margin-top: 15px;
  margin-bottom: 15px;
`;

const ModalBtnWrapper = styled.div`
  display: flex;
  justify-content: space-between;
  width: 100%;
`;

const ConfirmBtn = styled.button`
  width: 170px;
  height: 50px;
  font-size: 15px;
  font-weight: bold;
  color: var(--skyblue-500);
`;

const CancelBtn = styled(ConfirmBtn)`
  background-color: var(--white-100);
  color: var(--dark-blue-900);
`;

const Star = styled.span`
  margin-right: 10px;
`;

const ArtistCard = ({ imgSrc, imgAlt, nickName, artistName, membershipDate, handleDeleteBtnClick }) => (
  <Box>
    <Img>
      <img src={imgSrc} alt={imgAlt} />
    </Img>
    <NickName>
      {nickName}{' '}
      <button className='pen'>
        <i className='i-name-pen-icon' />
      </button>
    </NickName>
    <ArtistName>{artistName}</ArtistName>
    <MembershipDate>{membershipDate} 가입</MembershipDate>
    <DeleteBtn onClick={handleDeleteBtnClick}>커뮤니티 탈퇴하기</DeleteBtn>
  </Box>
);

const MyProfile = () => {
  const [showModal, setShowModal] = useState(false);
  const [membershipDate, setMembershipDate] = useState('2023-04-28');

  const handleDeleteBtnClick = () => {
    setShowModal(true);
  };

  const handleCancelBtnClick = () => {
    setShowModal(false);
  };

  const handleConfirmBtnClick = () => {
    setShowModal(false);
  };

  // useEffect(() => {
  //   // 서버로부터 회원 가입일 가져오기
  //   fetch('회원가입일자 API 엔드포인트')
  //     .then(response => response.json())
  //     .then(data => setMembershipDate(data.membershipDate))
  //     .catch(error => console.error('Error:', error));
  // }, []);

  return (
    <>
      <LeftWrapper>
        <LeftBox>
          <Name>TATA-V</Name>
          <Email>tata-v@example.com</Email>
          <LogoutBtn>로그아웃</LogoutBtn>
        </LeftBox>
        <Container>
          <Title>나의 프로필</Title>
          <ArtistCard
            imgSrc={BTS}
            imgAlt='BTS'
            nickName='TATA-V'
            artistName='BTS'
            membershipDate={membershipDate}
            handleDeleteBtnClick={handleDeleteBtnClick}
          />
          <ArtistCard
            imgSrc={TXT}
            imgAlt='TXT'
            nickName='TATA-T'
            artistName='TXT'
            membershipDate={membershipDate}
            handleDeleteBtnClick={handleDeleteBtnClick}
          />
          <ArtistCard
            imgSrc={NewJeans}
            imgAlt='NewJeans'
            nickName='TATA-J'
            artistName='NewJeans'
            membershipDate={membershipDate}
            handleDeleteBtnClick={handleDeleteBtnClick}
          />
        </Container>
      </LeftWrapper>
      {showModal && (
        <ModalWrapper>
          <ModalContent>
            <ModalTitle>커뮤니티를 탈퇴하시겠습니까?</ModalTitle>
            <ModalText>
              <Star>✰</Star>커뮤니티를 탈퇴하더라도 내가 작성한 콘텐츠는 삭제되지 않습니다. 프로필 사진과 닉네임도 함께 유지되어 노출됩니다.
            </ModalText>
            <ModalText>
              <Star>✰</Star>커뮤니티 내 모든 구독 정보가 초기화됩니다.
            </ModalText>
            <ModalText>
              <Star>✰</Star>커뮤니티의 MY 프로필 화면을 볼 수 없습니다.
            </ModalText>
            <ModalText>
              <Star>✰</Star>이 커뮤니티에서 진행된 혜택을 행사할 수 없습니다.
            </ModalText>
            <ModalTextBold>일부 정보와혜택은 재가입해도 복구되지 않습니다.</ModalTextBold>
            <ModalBtnWrapper>
              <CancelBtn onClick={handleCancelBtnClick}>취소</CancelBtn>
              <ConfirmBtn onClick={handleConfirmBtnClick}>탈퇴</ConfirmBtn>
            </ModalBtnWrapper>
          </ModalContent>
        </ModalWrapper>
      )}
    </>
  );
};

export default MyProfile;

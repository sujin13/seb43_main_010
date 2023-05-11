import styled, { css } from 'styled-components';
import deleteBtn from '../../../assets/png-file/x-btn.png';
import { useState, useRef, useEffect } from 'react';
//이미지아이콘 가져오기
import { BsFillCameraFill } from 'react-icons/bs';
// import HideArtist from './WritePostMaterial/HideArtist';

const WritePostBlock = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1;
  background-color: rgba(0, 0, 0, 0.7);
`;

const PostContentBox = styled.div`
  display: flex;
  transform: translateX(0);
  position: relative;

  .delete-btn {
    width: 45px;
    position: absolute;
    top: 9%;
    right: -45px;
  }
`;

const PostContent = styled.div`
  width: 767px;
  min-height: 540px;
  resize: none;

  background-color: var(--white-100);
  padding: 20px;
  border-radius: 20px;
  box-shadow: 0px 5px 15px rgb(19, 28, 35, 15%);

  .top-txt-box {
    height: 91px;
    width: 767px;
    border-radius: 20px 20px 0 0;
    background-color: var(--white-100);
    transform: translateX(-20px) translateY(-20px);

    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;

    .post-txt {
      color: var(--dark-blue-900);
      font-size: 19px;
      font-weight: 900;
    }
    .artist-txt {
      color: var(--light-gray-400);
      font-size: 13.5px;
      text-shadow: 0 0 0 var(--light-gray-400);
      transform: translateY(9px);
    }
  }

  .post-form {
    width: 767px;
    transform: translateX(-20px) translateY(-20px);
  }
`;

const BottomBox = styled.div`
  width: 767px;
  height: 75px;
  border-radius: 0 0 20px 20px;
  background-color: var(--white-100);
  transform: translateX(-20px) translateY(-20px);
  position: absolute;
  border-top: 1px solid var(--light-gray-150);

  display: flex;
  justify-content: space-between;
  align-items: center;

  .submit-btn {
    width: 55px;
    height: 38px;
    transform: translateX(-28px);
    border-radius: 8px;
    background-color: var(--light-gray-200);
    background: ${({ validity }) => (validity ? 'linear-gradient( -45deg, #1CBEC8, #FFCE4F )' : ' var(--light-gray-200)')};
    transition: 0.3s ease-in;

    display: flex;
    justify-content: center;
    align-items: center;

    span {
      color: ${({ validity }) => (validity ? 'var(--white-100)' : 'var(--light-gray-500)')};
      font-size: 14.5px;
      font-weight: 800;
      transition: 0.3s ease-in;
    }
  }

  .hide-block {
    display: flex;
    justify-content: center;
    align-items: center;
  }
`;

// 이미지 버튼
const ImgIcon = styled(BsFillCameraFill)`
  width: 32px;
  height: 32px;
  font-weight: 100;
  transform: translateX(28px);
  cursor: pointer;
`;
//이미지 인풋태그
const ImgInput = styled.input`
  display: none;
`;
//이미지 미리보기
const ImgPreview = styled.div`
  width: 100%;
  img {
    cursor: move;
    max-width: 100%;
  }
`;
// Feed와 Artist에서 쓰는 포스트 작성 창입니다.
// 사용하실 때 const [modalOpen, setModalOpen] = useState(false)를 상위에서 사용해 주세요!
const WriteModalPost = ({ modalOpen, setModalOpen }) => {
  const [content, setContent] = useState('');
  const [validity, setValidity] = useState(false);
  const [hide, setHide] = useState(false);

  const editorRef = useRef();
  const imgInput = useRef();
  const [imgList, setImgList] = useState([]);
  const [showImages, setShowImages] = useState([]);

  //이미지 아이콘 누르면 imgInput에 접근
  const onClickImgIcon = (e) => {
    e.preventDefault();
    imgInput.current.click();
  };

  //이미지 미리보기위한 함수
  const handleAddImg = (e) => {
    e.preventDefault();
    const imgLists = e.target.files;
    let imageUrlLists = [...showImages];

    if (imageUrlLists.length > 3) {
      alert('최대 4장 가능');
      return;
      // imageUrlLists = imageUrlLists.slice(0, 4);
    }

    for (let i = 0; i < imgLists.length; i++) {
      const currentImageUrl = URL.createObjectURL(imgLists[i]);
      imageUrlLists.push(currentImageUrl);
    }
    setShowImages(imageUrlLists);
    // focusEditor();

    // textInput.current.focus();

    // const fileArr = e.target.files;
    // const fileURLs = [];
    // let file;
    // let maxFile = 4;
    // let filesLength = fileArr.length > maxFile ? maxFile : fileArr.length;
    // if (fileArr.length > maxFile) {
    //   imgInput.current.value = '';
    //   alert(`한번에 업로드 가능한 사진은 최대 ${maxFile}장 까지 입니다.`);
    //   return;
    // }

    // for (let i = 0; i < filesLength; i++) {
    //   file = fileArr[i];
    //   let reader = new FileReader();
    //   console.log('1');
    //   //reader.readAsDataURL(file); 후에 실행된다
    //   //이게 실행될때까지 포문이 돌면안된다.
    //   reader.onload = () => {
    //     fileURLs[i] = reader.result; //인코딩된 파일
    //     setImgList([fileURLs[i], ...imgList]);
    //     console.log('2');
    //   };
    //   console.log('3');

    //   reader.readAsDataURL(file); //base64로 인코딩
    //   console.log('4');

    // if (file) {
    //   reader.readAsDataURL(file);
    // }
    // if (imgList.length > 4) {
    //   alert('사진은 최대 4장까지 등록이 가능합니다.');
    //   return;
    // }
    // imgInput.current.value = '';
  };
  const deleteBtnFn = () => {
    setModalOpen(false);
    setContent('');
  };

  // submit
  const submitFn = (e) => {
    e.preventDefault();
    // if (content.trim().length > 1) {
    //   // 여기서 서버한테 content 데이터 전송해야 함.
    //   // 서버에 데이터 전송 되면 내용 비우고 창 닫기
    //   // 조건을 더 추가해서 현재 로그인한 유저가 연예인인지 아닌지에 따라 데이터 전송하는 부분을 나누면 될 것 같아요.
    //   setContent('');
    //   setModalOpen(false);
    // }
  };

  return (
    <>
      {modalOpen ? (
        <WritePostBlock>
          <PostContentBox>
            <PostContent>
              <div className='top-txt-box'>
                <span className='post-txt'>포스트 쓰기</span>
              </div>
              <div className='post-form'>
                {/* <Editor className='editor' contentEditable='true' suppressContentEditableWarning='true' onInput={onContextnHandler} ref={editorRef}>
                  {showImages.map((el, id) => (
                    <>
                      <ImgPreview key={id}>
                        <img src={el} alt='preview-img'></img>
                      </ImgPreview>
                    </>
                  ))}
                </Editor> */}
                <ImgInput
                  className='img-post'
                  type='file'
                  multiple='multiple'
                  name='img-post'
                  placeholder='이미지업로드'
                  accept='image/*'
                  ref={imgInput}
                  onChange={handleAddImg}
                ></ImgInput>
              </div>

              <BottomBox validity={validity} hide={hide}>
                {/* 현아님! 이미지 추가버튼입니다 */}
                <ImgIcon onClick={onClickImgIcon} />
                <div className='hide-block'>
                  {/* HideArtist 컴포넌트, 아티스트인지 아닌지 여부에 따라 notArtist에 값을 넣어주는 거로 수정해야 함 */}
                  {/* <HideArtist notArtist='true' setHide={setHide} hide={hide} /> */}

                  <button onClick={submitFn} className='submit-btn'>
                    <span>등록</span>
                  </button>
                </div>
              </BottomBox>
            </PostContent>
            <button onClick={deleteBtnFn} className='delete-btn'>
              <img src={deleteBtn} alt='delete-button' />
            </button>
          </PostContentBox>
        </WritePostBlock>
      ) : null}
    </>
  );
};

export default WriteModalPost;

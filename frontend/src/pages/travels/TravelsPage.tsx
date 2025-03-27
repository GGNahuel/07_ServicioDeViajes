import { css } from "@emotion/react";
import { useClick, useDismiss, useFloating, useInteractions } from "@floating-ui/react";
import { useState } from "react";
import { MainSection } from "../../components/MainSection";
import { useTravelSearcher } from "../../requests/TravelRequests";
import { Travel } from "../../types/ApiTypes";
import { TravelCard } from "./components/TravelCard";
import { TravelContent } from "./sections/TravelContent";
import { TravelsSearchForm } from "./sections/TravelsSearchForm";
import { useWindowProps } from "../../hooks/useWindowsProps";

export function TravelsPage() {
  const {response, makeRequest} = useTravelSearcher()
  const [selectedTravel, setSelectedTravel] = useState<Travel>()
  const [isModalOpen, setIsModalOpen] = useState(false)
  const {width} = useWindowProps()

  const {context} = useFloating({
    open: isModalOpen,
    onOpenChange: setIsModalOpen,
  });

  const click = useClick(context);
  const dismiss = useDismiss(context);
  const {getReferenceProps, getFloatingProps} = useInteractions([click, dismiss]);

  const handleOnClickInTravelCard = (travel: Travel) => {
    setSelectedTravel(travel)
    setIsModalOpen(true)
  }

  const modalStyles = css`
    background-color: rgb(220,220,220);
    border: 2px solid black;
    position: fixed;
    top: 50%;
    transform: translateY(-50%);
    z-index: 15;
    padding: 1rem;
    width: 100%;
    max-width: 1136px;
    border-radius: 8px;
    ${width > 720 ? "max-height: 90vh; overflow-y: auto;" : ""}
  `

  return (
    <main>
      <TravelsSearchForm makeRequest={makeRequest} />
      <MainSection variant="row" styles={css`
        gap: 1rem;
        flex-wrap: wrap;
        align-items: stretch;
      `}>    
        {response?.map(travel => <TravelCard travel={travel} onClick={handleOnClickInTravelCard} referencePropsFloatingUI={getReferenceProps}/>)}
        {isModalOpen && selectedTravel &&
          <section css={modalStyles} {...getFloatingProps()}>
            <TravelContent travel={selectedTravel} handleCloseIfModal={() => setIsModalOpen(false)} />
          </section>
        }
      </MainSection>
    </main>
  )
}